package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.CreateOrderDTO;
import com.utn.ProgIII.dto.CreatedOrderDTO;

public interface OrderService {
    CreatedOrderDTO createOrder(CreateOrderDTO createOrderDTO);
}
