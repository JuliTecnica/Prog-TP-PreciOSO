package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.dto.ViewProductCustomer;
import com.utn.ProgIII.service.interfaces.CustomerService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/sales")
public class CustomerControler {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/on-sale")
    @ApiResponse(responseCode = "200", description = "Datos encontrados", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
    ))
    public ResponseEntity<Page<ViewProductCustomer>> getProductsOnSale(
            @ParameterObject @PageableDefault(size = 5) Pageable paginacion,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Long> category,
            @RequestParam(required = false) boolean include_oos
    )
    {
        return ResponseEntity.ok(customerService.getProductsOnSale(paginacion, name, category, include_oos));
    }


    @GetMapping("/product/{id}")
    @ApiResponse(responseCode = "200", description = "Datos encontrados", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ViewProductCustomer.class)
    ))
    @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    public ResponseEntity<ViewProductCustomer> getProductOnSale(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(customerService.getProductOnSale(id));
    }

    @GetMapping("/products/{productIds}")
    @ApiResponse(responseCode = "200", description = "Datos encontrados", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
    ))
    public ResponseEntity<List<ViewProductCustomer>> getProductsOnSaleByPage(
            @PathVariable List<Long> productIds
    )
    {
        return ResponseEntity.ok(customerService.getProductsOnCart(productIds));
    }
}
