package com.utn.ProgIII.service.implementations;

import com.querydsl.core.BooleanBuilder;
import com.utn.ProgIII.dto.ViewProductCustomer;
import com.utn.ProgIII.exceptions.NotFoundException;
import com.utn.ProgIII.mapper.ProductMapper;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.model.Product.QProduct;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;


    @Override
    public ViewProductCustomer getProductOnSale(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("El producto no existe!"));

        if(product.getStatus() == ProductStatus.DISABLED)
        {
            throw new NotFoundException("El producto no existe!");
        }


        return productMapper.toViewCustomerDTO(product);
    }

    @Override
    public Page<ViewProductCustomer> getProductsOnSale(Pageable pageable, String name, List<Long> categories, boolean include_oos) {
        QProduct qProduct = QProduct.product;
        BooleanBuilder booleanBuilder = new BooleanBuilder().or(qProduct.isNotNull());

        booleanBuilder.and(qProduct.status.eq(ProductStatus.ENABLED));
        booleanBuilder.and(qProduct.price.isNotNull());

        if(name != null)
        {
            booleanBuilder.and(qProduct.name.likeIgnoreCase('%' + name + '%'));
        }

        if(categories != null && !categories.isEmpty())
        {
            booleanBuilder.and(qProduct.category.idCategory.in(categories));
        }

        if(include_oos)
        {
            booleanBuilder.and(qProduct.stock.goe(0));
        } else {
            booleanBuilder.and(qProduct.stock.goe(1));
        }

        return productRepository.findAll(booleanBuilder, pageable).map(productMapper::toViewCustomerDTO);
    }
}
