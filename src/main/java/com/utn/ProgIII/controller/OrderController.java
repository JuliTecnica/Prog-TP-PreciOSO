package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.BasicUserInfoDTO;
import com.utn.ProgIII.dto.CreatedOrderDTO;
import com.utn.ProgIII.dto.ViewOrderDTO;
import com.utn.ProgIII.service.interfaces.OrderService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/orders")
@Tag(name = "Productos y Proveedores", description = "Operaciones relacionadas con la relación de productos y proveedores")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiResponse(responseCode = "200", description = "Busca de datos segun parametros", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = CreatedOrderDTO.class))
    ))
    @GetMapping("")
    public ResponseEntity<Page<ViewOrderDTO>> viewOrders(
            @ParameterObject @PageableDefault(size = 10) Pageable paginacion,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String dni
    )
    {
        return ResponseEntity.ok(orderService.getOrdersPage(paginacion, status, dni));
    }
}
