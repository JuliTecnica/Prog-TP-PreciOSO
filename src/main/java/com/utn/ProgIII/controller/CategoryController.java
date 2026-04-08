package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.AddCategoryDTO;
import com.utn.ProgIII.dto.CategoryDTO;
import com.utn.ProgIII.service.interfaces.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categorias", description = "Realiza operaciones con categorias")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Añade categorias para productos")
    @ApiResponse(responseCode = "201", description = "Creación exitosa de categoria", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CategoryDTO.class)
    ))
    @ApiResponse(responseCode = "400", description = "Error en datos introducidos", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @PostMapping("")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody AddCategoryDTO categoryDTO) {
        return ResponseEntity.status(201).body(categoryService.addCategory(categoryDTO));
    }

    @Operation(summary = "Elimina categorias para productos")
    @ApiResponse(responseCode = "204", description = "Eliminación exitosa")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCategory(@PathVariable Long id)
    {
        categoryService.removeCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modifica categorias existentes")
    @ApiResponse(responseCode = "200", description = "Actualización exitosa", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CategoryDTO.class)
    ))
    @ApiResponse(responseCode = "400", description = "Error en los datos introductidos", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody AddCategoryDTO categoryDTO, @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.modifyCategory(categoryDTO, id));
    }

    @Operation(summary = "Lista todas las categorias cargadas")
    @ApiResponse(responseCode = "200", description = "Categorias cargadas", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))
    ))
    @ApiResponse(responseCode = "404", description = "Ninguna categoria cargada.", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProblemDetail.class))
    ))
    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
