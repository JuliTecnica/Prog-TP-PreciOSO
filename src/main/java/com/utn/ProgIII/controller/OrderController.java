package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CreatedOrderDTO;
import com.utn.ProgIII.dto.StateChangeDTO;
import com.utn.ProgIII.dto.ViewOrderDTO;
import com.utn.ProgIII.service.interfaces.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con pedidos")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiResponse(responseCode = "200", description = "Datos encontrados", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = CreatedOrderDTO.class))
    ))
    @GetMapping("")
    @Operation(summary = "Busca pedidos segun el estado y dni", description = "Busca pedidos segun el estado y el dni ingresado y genera una pagina")
    public ResponseEntity<Page<ViewOrderDTO>> viewOrders(
            @ParameterObject @PageableDefault(size = 10) @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable paginacion,
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
    @Operation(summary = "Busca un pedido por ID", description = "Busca un pedido por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ViewOrderDTO> viewOrders(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @ApiResponse(responseCode = "204", description = "Cambio de datos", content = @Content())
    @ApiResponse(responseCode = "403", description = "Acción no permitida", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @PatchMapping("/{id}")
    @Operation(summary = "Modifica el estado de un pedido según ID", description = "Modifica el estado de un pedido")
    public ResponseEntity<?> changeOrderState(
            @PathVariable Long id,
            @RequestBody StateChangeDTO status
    ) {
        orderService.changeOrderStatus(id,status);
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "200", description = "Datos encontrados", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ViewOrderDTO.class))
    ))
    @Operation(summary = "Busca pedidos del usuario", description = "Busca los pedidos del usuario que hace el pedido")
    @GetMapping("/my-orders")
    public ResponseEntity<Page<ViewOrderDTO>> viewMyOrders(
            @RequestParam(required = false) String status,
            @ParameterObject @PageableDefault(size = 10) @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable paginacion
    )
    {
        return ResponseEntity.ok(orderService.getMyOrders(status, paginacion));
    }
}
