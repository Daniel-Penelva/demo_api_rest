package com.project.demo_api_rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo_api_rest.model.Category;
import com.project.demo_api_rest.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Utilizando Swagger: http://localhost:8080/swagger-ui.html


    // http://localhost:8080/categories/create
    @Operation(summary = "Cria uma nova categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
        @ApiResponse(responseCode = "409", description = "Id já existe")
    })
    @PostMapping("/create")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }


    /* ===================================================================================== */

    // http://localhost:8080/categories/all
    @Operation(summary = "Lista todas as Categorias")
    @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso")
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    /* ===================================================================================== */

    // http://localhost:8080/categories/1
    @Operation(summary = "Busca uma Categoria pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }


    /* ===================================================================================== */

    // http://localhost:8080/categories/update/1
    @Operation(summary = "Atualizar uma Categoria existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }


    /* ===================================================================================== */

    // http://localhost:8080/categories/delete/1

    @Operation(summary = "Remove uma Categoria pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
