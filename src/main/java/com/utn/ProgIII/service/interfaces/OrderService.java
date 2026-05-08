package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.CreateOrderDTO;
import com.utn.ProgIII.dto.CreatedOrderDTO;
import com.utn.ProgIII.dto.ViewOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    CreatedOrderDTO createOrder(CreateOrderDTO createOrderDTO);

    Page<ViewOrderDTO> getOrdersPage(Pageable pageable, String status, String dni);
}
