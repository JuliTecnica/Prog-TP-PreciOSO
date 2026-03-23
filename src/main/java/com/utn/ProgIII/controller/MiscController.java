package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.ViewDolarDTO;
import com.utn.ProgIII.service.interfaces.MiscService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase que se usa para cosas misceláneas, no necesariamente relacionadas con el modelo funcional
 */
@RestController
@RequestMapping("/misc")
@Tag(name = "Misceláneos", description = "Operaciones relacionadas con acciones misceláneas")
@ApiResponse(responseCode = "403", description = "Acceso prohibido/dirección no encontrada", content = @Content())
public class MiscController {

    @Autowired
    private MiscService miscservice;


    @GetMapping("/dollar")
    @Operation(summary = "Valor actual del dólar oficial", description = "Muestra un objeto del valor actual del dólar oficial\n Puede retornar otros códigos de errores externos a este servicio.")
    @ApiResponse(responseCode = "200",description = "Precio encontrado", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ViewDolarDTO.class)
    ))
    @ApiResponse(responseCode = "404", description = "Cotización no existente", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @ApiResponse(responseCode = "500",description = "Error interno en el servidor", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    public ResponseEntity<ViewDolarDTO> viewDollarPrice(@Parameter(description = "Un tipo de cotización disponible en dolarapi.com",required = false) @RequestParam(required = false, defaultValue = "oficial") String exchange_rate)
    {
        return ResponseEntity.ok().body(miscservice.searchDollarPrice(exchange_rate));
    }
}
