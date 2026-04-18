package com.utn.ProgIII.service.implementations;

import com.querydsl.core.BooleanBuilder;
import com.utn.ProgIII.csv.CsvReader;
import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.exceptions.*;
import com.utn.ProgIII.mapper.ProductSupplierMapper;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.model.ProductSupplier.*;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.repository.ProductSupplierRepository;
import com.utn.ProgIII.repository.SupplierRepository;
import com.utn.ProgIII.service.interfaces.AuthService;
import com.utn.ProgIII.service.interfaces.MiscService;
import com.utn.ProgIII.service.interfaces.ProductSupplierService;
import com.utn.ProgIII.validations.ProductSupplierValidations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
/*
 * Un servicio que se encarga de hacer funciones para la relación entre productos y proveedores
 */
public class ProductSupplierServiceImpl implements ProductSupplierService {

    private final ProductSupplierRepository productSupplierRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductSupplierMapper mapper;
    private final ProductSupplierValidations productSupplierValidations;
    private final CsvReader csvReader;
    private final MiscService miscService;

    public ProductSupplierServiceImpl(ProductSupplierRepository productSupplierRepository,
                                      ProductRepository productRepository,
                                      SupplierRepository supplierRepository,
                                      ProductSupplierMapper mapper,
                                      ProductSupplierValidations productSupplierValidations,
                                      CsvReader csvReader,
                                      MiscService miscService
    ){

        this.productSupplierRepository = productSupplierRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
        this.productSupplierValidations = productSupplierValidations;
        this.csvReader = csvReader;
        this.miscService = miscService;
    }


    /**
     * Se crea una nueva relación entre un producto y proveedor, con su precio y margen de ganancia
     * @param createProductSupplierDTO Un DTO que se usa crear una relación
     * @return Un DTO de la relación entre el producto y proveedor creada
     */
    @Override
    public ResponseProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO) {

        Supplier supplier = supplierRepository.findById(createProductSupplierDTO.idSupplier())
                .orElseThrow(() -> new SupplierNotFoundException("No existe el proveedor con ese ID"));

        Product product = productRepository.findById(createProductSupplierDTO.idProduct())
                .orElseThrow( ()-> new ProductNotFoundException("No existe producto con ese ID"));

        if(product.getStatus() == ProductStatus.DISABLED)
        {
            throw new ProductNotFoundException("Ese producto está desactivado, activarlo y intente de nuevo");
        }

        ProductSupplier productSupplier = new ProductSupplier(
                supplier,
                product,
                createProductSupplierDTO.cost()
        );

        productSupplierValidations.validateRelationship(productSupplier);

        Optional<Double> optional = productSupplierRepository.findTopCostInProduct(productSupplier.getProduct().getIdProduct());


        boolean change_price = false;

        if(optional.isEmpty())
        {
            change_price = true;
        } else if (optional.get() < createProductSupplierDTO.cost()) {
            change_price = true;
        }


        if(change_price)
        {
            product.setPrice(createProductSupplierDTO.cost());
            productRepository.save(product);
        }

        productSupplierRepository.save(productSupplier);

        return mapper.fromEntityToDto(productSupplier);
    }

    @Override
    public void deleteProductSupplier(Long idProductSupplier) {
        ProductSupplier productSupplier = productSupplierRepository.findById(idProductSupplier).orElseThrow(() -> new NotFoundException("Ese relación no existe!"));
        Optional<Double> optional = productSupplierRepository.findTopCostInProduct(productSupplier.getProduct().getIdProduct());
        boolean prices_match = optional.isPresent() && optional.get().equals(productSupplier.getCost());

        productSupplierRepository.delete(productSupplier);

        if(prices_match)
        {
            optional = productSupplierRepository.findTopCostInProduct(productSupplier.getProduct().getIdProduct());
            Product product = productSupplier.getProduct();

            if(optional.isPresent())
            {
                product.setPrice(optional.get());
            } else {
                product.setPrice(null);
            }

            productRepository.save(product);
        }

    }

    /**
     * Se actualiza una relación existente, con sus datos
     * @param updateProductSupplierDTO Un objeto con los datos para modificar la relación
     * @param id El ID para la relación a editar
     * @return Un DTO con los datos modificados
     */
    @Override
    public ResponseProductSupplierDTO updateProductSupplier(UpdateProductSupplierDTO updateProductSupplierDTO, Long id) {

        ProductSupplier productSupplier = productSupplierRepository.findById(id)
                .orElseThrow(() -> new ProductSupplierNotExistException("La relación que quiere editar no se encuentra"));

        Double newCost = updateProductSupplierDTO.cost();
        productSupplier.setCost(newCost);

        Product product = productSupplier.getProduct();

        productSupplierRepository.save(productSupplier);
        Optional<Double> optional = productSupplierRepository.findTopCostInProduct(productSupplier.getProduct().getIdProduct());

        boolean modify_product = false;
        if(optional.isEmpty())
        {
            modify_product = true;
        } else if (optional.get() <= updateProductSupplierDTO.cost()) {
            modify_product = true;
        }

        if(modify_product)
        {
            product.setPrice(updateProductSupplierDTO.cost());
            productRepository.save(product);
        }


        return mapper.fromEntityToDto(productSupplier);
    }

    /**
     * Lista las relaciones según el nombre de la empresa, los datos mostrados varían según el nivel de acceso del usuario
     *
     * @param companyId   Id del proveedor para buscar
     * @param exchange_rate Tipo de cotizacion de dolarapi.com
     * @return Una lista de DTO con los datos para mostrar
     */
    @Override
    public SupplierProductListDTO listProductsBySupplier(Pageable pageable, Long companyId, String exchange_rate) {

        Supplier supplier = supplierRepository.findById(companyId)
                .orElseThrow(() -> new SupplierNotFoundException("El proveedor no existe"));

        QProductSupplier qProductSupplier = QProductSupplier.productSupplier;
        BooleanBuilder booleanBuilder = new BooleanBuilder().or(qProductSupplier.isNotNull());
        booleanBuilder.and(qProductSupplier.supplier.idSupplier.eq(companyId));

        Page<?> priceList;
        Double dolar = -1.0;

        try {
            dolar = miscService.searchDollarPrice(exchange_rate).venta();
        } catch (UnexpectedServerErrorException ignored) {}

        if(dolar != -1.0)
        {
            Double finalDolar = dolar;
            priceList = productSupplierRepository.findAll(booleanBuilder, pageable).map((x) -> mapper.fromEntityToExtendedProductDTO(x, finalDolar));
        } else {
            priceList = productSupplierRepository.findAll(booleanBuilder, pageable).map(mapper::fromEntityToExtendedProductDTODolarless);
        }

        return new SupplierProductListDTO(
                supplier.getIdSupplier(),
                supplier.getCompanyName(),
                priceList
        );
    }

    /**
     * Lista las relaciones según el ID de un producto cargado, los datos mostrados varían según el nivel de acceso del usuario
     *
     * @param idProduct     El ID del producto
     * @param exchange_rate Tipo de cotizacion de dolarapi.com
     * @return Una lista de DTO para mostrar
     */
    public ProductPricesDTO listPricesByProduct(Pageable pageable, Long idProduct, String exchange_rate) {
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new ProductNotFoundException("El producto no existe"));

        if (product.getStatus() == ProductStatus.DISABLED) {
            throw new ProductNotFoundException("El producto está desactivado, y no tendrá precios.");
        }


        QProductSupplier qProductSupplier = QProductSupplier.productSupplier;
        BooleanBuilder booleanBuilder = new BooleanBuilder().or(qProductSupplier.isNotNull());
        booleanBuilder.and(qProductSupplier.product.idProduct.eq(idProduct));

        Page<?> priceList;
        Double dolar = -1.0;

        try {
            dolar = miscService.searchDollarPrice(exchange_rate).venta();
        } catch (UnexpectedServerErrorException ignored) {}

        if(dolar != -1.0)
        {
            Double finalDolar = dolar;
            priceList = productSupplierRepository.findAll(booleanBuilder, pageable).map((x) -> mapper.fromEntityToExtendedSupplierDTO(x, finalDolar));
        } else {
            priceList = productSupplierRepository.findAll(booleanBuilder, pageable).map(mapper::fromEntityToExtendedSupplierDTODolarless);
        }

        return new ProductPricesDTO(product.getIdProduct(),product.getName(),priceList);
    }

    /**
     * Se usa para modificar relaciones existentes
     *
     * @param filepath   Path interno del archivo
     * @param idSupplier El ID del proveedor para modificar sus relaciones
     * @param mode El modo de cambio en para usar en el csv
     */
    @Override
    public NonAffectedProductsListDTO uploadCsv(String filepath, Long idSupplier, String mode) {

        if(!supplierRepository.existsById(idSupplier))
        {
            throw new SupplierNotFoundException("El proveedor asignado no existe");
        }

        return switch (mode) {
            case "modify" -> csvReader.modifyRels(filepath, idSupplier);
            case "add" -> csvReader.createRels(filepath, idSupplier);
            default -> throw new BadRequestException("Esta opción no existe!");
        };
    }

    @Override
    public ResponseProductSupplierDTO getProductSupplier(Long id) {
        this.productSupplierValidations.productSupplierIdExist(id);

        return this.mapper.fromEntityToDto(this.productSupplierRepository.getReferenceById(id)) ;
    }
}
