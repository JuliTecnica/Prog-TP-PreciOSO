package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreatedOrderDTO;
import com.utn.ProgIII.dto.StateChangeDTO;
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
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/orders")
@Tag(name = "Productos y Proveedores", description = "Operaciones relacionadas con la relación de productos y proveedores")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiResponse(responseCode = "200", description = "Datos encontrados", content = @Content(
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

    @ApiResponse(responseCode = "200", description = "Datos encontrados", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = CreatedOrderDTO.class))
    ))
    @ApiResponse(responseCode = "404", description = "Datos no encontrados", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @GetMapping("/{id}")
    public ResponseEntity<ViewOrderDTO> viewOrders(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @ApiResponse(responseCode = "204", description = "Cambio de datos", content = @Content())
    @PatchMapping("/{id}")
    public ResponseEntity<?> changeOrderState(
            @PathVariable Long id,
            @RequestBody StateChangeDTO status
    ) {
        orderService.changeOrderStatus(id,status);
        return ResponseEntity.noContent().build();
    }
}
