package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.ViewProductCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    ViewProductCustomer getProductOnSale(Long id);
    Page<ViewProductCustomer> getProductsOnSale(Pageable pageable, String name, List<Long> categories, boolean include_oos);
}
