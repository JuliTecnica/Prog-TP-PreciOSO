package com.utn.ProgIII.serviceTest;

import com.utn.ProgIII.dto.CreateProductDTO;
import com.utn.ProgIII.exceptions.DuplicateEntityException;
import com.utn.ProgIII.exceptions.InvalidProductStatusException;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import com.utn.ProgIII.exceptions.ProductNotFoundException;
import com.utn.ProgIII.mapper.ProductMapper;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.repository.ProductSupplierRepository;
import com.utn.ProgIII.service.implementations.ProductServiceImpl;
import com.utn.ProgIII.service.interfaces.AuthService;
import com.utn.ProgIII.validations.ProductValidations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;
    @Mock
    ProductSupplierRepository productSupplierRepository;
    @Mock
    AuthService authService;
    @Mock
    ProductValidations productValidations;

    @InjectMocks
    ProductServiceImpl productService;

    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "product One";
    private static final Long PRODUCT2_ID = 3L;
    private static final String PRODUCT2_NAME = "One product";
    private static final ProductStatus STATUS_ENABLED = ProductStatus.ENABLED;
    private static final ProductStatus STATUS_DISABLED = ProductStatus.DISABLED;
    private static final String INVALID_STATUS = "Enbled";
    private static final Long NON_EXISTING_ID = 2L;

    private Product product;
    private CreateProductDTO productDTO;

    @BeforeEach
    void setUp(){

        product = createBasicEnabledProduct();
        productDTO = createBasicProductDto();

    }

    private Product createBasicEnabledProduct(){
        Product product = new Product();
        product.setIdProduct(PRODUCT_ID);
        product.setName(PRODUCT_NAME);
        product.setStatus(STATUS_ENABLED);

        return product;
    }

    private Product createDisabledProduct(){
        Product product = createBasicEnabledProduct();
        product.setName(PRODUCT2_NAME);
        product.setStatus(STATUS_DISABLED);
        return product;
    }

    private CreateProductDTO createBasicProductDto(){
        return new CreateProductDTO(PRODUCT_ID, PRODUCT_NAME, STATUS_ENABLED.toString(),20.0,2, 2L);
    }

    private CreateProductDTO createBasicDisabledProductDto(){
        return new CreateProductDTO(PRODUCT2_ID, PRODUCT2_NAME, STATUS_DISABLED.toString(), 20.0, 2, 2L);
    }

    @Test
    public void getProductById_shouldReturnProductDTO_whenProductExists(){

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);

        CreateProductDTO result = productService.getProductById(PRODUCT_ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.idProduct(), PRODUCT_ID);
        Assertions.assertEquals(result.name(), PRODUCT_NAME);
        Assertions.assertEquals(result.status(), STATUS_ENABLED.toString());

        Mockito.verify(productRepository).findById(PRODUCT_ID);
        Mockito.verify(productMapper).toProductDTO(product);

    }

    @Test
    public void getUserById_shouldThrowException_whenProductNotExists(){

        when(productRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(NON_EXISTING_ID));

        Mockito.verify(productRepository).findById(NON_EXISTING_ID);
    }

    @Test
    public void getUserBy_shouldThrowException_whenUserIsEmployeeAndProductIsDisabled(){

        Product disabledProduct = createDisabledProduct();

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(disabledProduct));
        when(authService.isEmployee()).thenReturn(true);

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(PRODUCT_ID));

        verify(productRepository).findById(PRODUCT_ID);
        verify(authService).isEmployee();
        verify(productMapper, never()).toProductDTO(any());
    }


    @Test
    void getUserById_shouldReturnProduct_whenEmployeeAndProductEnabled(){


        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        when(authService.isEmployee()).thenReturn(true);
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);

        CreateProductDTO result = productService.getProductById(PRODUCT_ID);

        assertEquals(productDTO, result);
        verify(productMapper).toProductDTO(product);

    }

    @Test
    void getAllProducts_shouldReturnListOfAllProducts_whenUserManager(){
        Product disabledProduct = createDisabledProduct();
        CreateProductDTO disableProductDto = createBasicDisabledProductDto();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(disabledProduct);


        when(productRepository.findAll()).thenAnswer(
                invocation -> productList
        );
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);
        when(productMapper.toProductDTO(disabledProduct)).thenReturn(disableProductDto);

        List<CreateProductDTO> result = productService.getAllProduct();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(productDTO, disableProductDto)));
        verify(productRepository).findAll();
        verify(productRepository, never()).findByStatus(any());
    }

    @Test
    void getAllProducts_shouldReturnOnlyEnabledProducts_whenUserEmployee(){

        List<Product> onlyEnabledProductList = new ArrayList<>();
        onlyEnabledProductList.add(product);

        when(authService.isEmployee()).thenReturn(true);
        when(productRepository.findByStatus(STATUS_ENABLED)).thenAnswer(
                invocation -> onlyEnabledProductList
        );

        when(productMapper.toProductDTO(product)).thenReturn(productDTO);

        List<CreateProductDTO> result = productService.getAllProduct();

        assertEquals(1, result.size());
        assertTrue(result.contains(productDTO));
        verify(productRepository).findByStatus(STATUS_ENABLED);


    }

    @Test
    void getAllProductsByState_shouldReturnListOfSameStateProducts(){

        List<Product> onlyEnabledProductList = new ArrayList<>();
        onlyEnabledProductList.add(product);

        when(productRepository.findByStatus(STATUS_ENABLED)).thenAnswer(
                invocationOnMock -> onlyEnabledProductList
        );
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);

        List<CreateProductDTO> result = productService.getAllProductByStatus(STATUS_ENABLED.toString());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(productDTO));

    }

    @Test
    void getAllProductsByStatus_shouldThrowAnException_whenStatusIsInvalid(){

       InvalidProductStatusException exception = assertThrows(InvalidProductStatusException.class,
        () -> productService.getAllProductByStatus(INVALID_STATUS));

       assertEquals("El estado ingresado es inválido", exception.getMessage());

       verifyNoInteractions(productRepository);
       verifyNoInteractions(productMapper);

    }

    @Test
    void getProductByName_shouldReturnAListOfEnabledProducts_whenUserEmployee(){

        List<Product> onlyEnabledProductList = new ArrayList<>();
        onlyEnabledProductList.add(product);

        when(productRepository.findByNameContaining("One")).thenAnswer(
                invocationOnMock -> onlyEnabledProductList
        );
        when(authService.isEmployee()).thenReturn(true);
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);

        List<CreateProductDTO> result = productService.getProductByName("One");

        assertEquals(1, result.size());
        assertEquals(result.get(0).name(), "product One");
        assertEquals(result.get(0).status(), "ENABLED");
        verify(productRepository).findByNameContaining("One");
        verify(productMapper).toProductDTO(product);
    }

    @Test
    void getProductByName_shouldReturnAListOfAllProducts_whenUserManager(){

        Product disabledProduct = createDisabledProduct();
        CreateProductDTO disabledProductDTO = createBasicDisabledProductDto();

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(disabledProduct);

        when(productRepository.findByNameContaining("One")).thenAnswer(
                invocationOnMock -> productList
        );
        when(authService.isEmployee()).thenReturn(false);
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);
        when(productMapper.toProductDTO(disabledProduct)).thenReturn(disabledProductDTO);

        List<CreateProductDTO> result = productService.getProductByName("One");

        assertEquals(2, result.size());
        assertEquals(result.get(0).name(), "product One");
        assertEquals(result.get(0).status(), "ENABLED");
        assertEquals(result.get(1).name(), "One product");
        assertEquals(result.get(1).status(), "DISABLED");
        verify(productRepository).findByNameContaining("One");
        verify(productMapper).toProductDTO(product);
    }

    @Test
    void createProductDto_shouldReturnACreatedProduct_whenValidInput(){

       CreateProductDTO inputDTO = new CreateProductDTO(null, PRODUCT_NAME, STATUS_ENABLED.toString());
       Product nullIdProduct = createBasicEnabledProduct();
       nullIdProduct.setIdProduct(null);

        when(productMapper.toEntity(inputDTO)).thenReturn(nullIdProduct);
        when(productRepository.save(nullIdProduct)).thenReturn(product);
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);

        CreateProductDTO result = productService.createProductDto(inputDTO);

        assertNotNull(result);
        assertEquals(1L, result.idProduct());
        assertEquals(PRODUCT_NAME, result.name());

        verify(productValidations).validateProductNameExists(nullIdProduct);
        verify(productMapper).toEntity(inputDTO);
        verify(productRepository).save(nullIdProduct);
        verify(productMapper).toProductDTO(product);

    }

    @Test
    void createProductDTO_shouldThrowAnException_whenProductAlreadyExist(){

        when(productMapper.toEntity(productDTO)).thenReturn(product);
        doThrow(new DuplicateEntityException("El producto ya existe"))
                .when(productValidations).validateProductNameExists(product);

        assertThrows(DuplicateEntityException.class,
                () -> productService.createProductDto(productDTO));

        verify(productValidations).validateProductNameExists(product);
        verify(productRepository, never()).save(any());
        verify(productMapper, never()).toProductDTO(any());

    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct_whenValidInput(){

        Product initialProduct = createBasicEnabledProduct();
        initialProduct.setStatus(STATUS_DISABLED);

        CreateProductDTO updateDto = new CreateProductDTO(PRODUCT_ID, "product Two", STATUS_ENABLED.toString());

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(initialProduct));
        when(productRepository.save(any(Product.class)))
                .thenAnswer( invocationOnMock -> invocationOnMock.getArgument(0) );

        when(productMapper.toProductDTO(any(Product.class))).thenReturn(updateDto);

        CreateProductDTO result = productService.updateProduct(PRODUCT_ID, updateDto);

        assertEquals("product Two", result.name());
        assertEquals("ENABLED", result.status());

        verify(productRepository).findById(PRODUCT_ID);
        verify(productRepository).save(any(Product.class));
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productMapper).toProductDTO(productArgumentCaptor.capture());

        Product savedProduct = productArgumentCaptor.getValue();
        assertEquals("product Two", savedProduct.getName());
        assertEquals(ProductStatus.ENABLED, savedProduct.getStatus());

    }

    @Test
    void updateProduct_shouldThrowAnException_whenStatusInvalid(){

        CreateProductDTO invalidDTO = new CreateProductDTO(PRODUCT_ID, PRODUCT_NAME, INVALID_STATUS);

        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> productService.updateProduct(PRODUCT_ID, invalidDTO));

        assertEquals("El estado de producto ingresado no es válido", exception.getMessage());
        verify(productRepository, never()).save(any());
        verify(productMapper, never()).toProductDTO(any());

    }

    @Test
    void deleteProduct_shouldDisableProductAndRemoveSupplier_whenProductExists(){

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

        doNothing().when(productSupplierRepository).removeAllByProduct_IdProduct(PRODUCT_ID);

        productService.deleteProduct(PRODUCT_ID);

        assertEquals(ProductStatus.DISABLED, product.getStatus());

        verify(productRepository).findById(PRODUCT_ID);
        verify(productSupplierRepository).removeAllByProduct_IdProduct(PRODUCT_ID);
        verify(productRepository).save(product);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        assertEquals(ProductStatus.DISABLED, savedProduct.getStatus());

    }

    @Test
    void deleteProduct_shouldThrowException_whenProductNotFound(){

        when(productRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(NON_EXISTING_ID));

        verify(productRepository, never()).save(any());
        verify(productSupplierRepository, never()).removeAllByProduct_IdProduct(any());

    }

}
