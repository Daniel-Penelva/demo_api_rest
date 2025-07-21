package com.project.demo_api_rest.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.demo_api_rest.enums.ProductState;
import com.project.demo_api_rest.model.Product;
import com.project.demo_api_rest.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Utilizando Swagger: http://localhost:8080/swagger-ui.html

    /* Exemplo 1: Não Aconselhável fazer assim!!!
     * Este exemplo não segue boas práticas REST. 
     * Por que não específica a forma de retorno, no caso, retorna o status 200 (OK).
     * Além de não seguir boas práticas REST.
     * É ambíguo em relação a criação de recursos.
     * http://localhost:8080/products/create-example1
    */
    @Operation(summary = "Cria uma novo produto - não segue boas práticas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "409", description = "Id já existe")
    })
    @PostMapping("/create-example1")
    public ResponseEntity<Product> addProductOtherExample(@RequestBody @Valid Product product) {
        System.out.println("Produto recebido na controller: " + product); // Adicione este log
        return ResponseEntity.ok(productService.addProduct(product));
    }


    /* ===================================================================================== */
    /* Exemplo 2: Aconselhável fazer assim!!!
     * Este exemplo segue boas práticas REST. 
     * Por que específica a forma de retorno, no caso, retorna o status 201 (status(HttpStatus.CREATED)).
     * Ou seja, segui boas práticas REST e não é ambíguo em relação a criação de recursos.
     * http://localhost:8080/products/create-example2
    */
    @Operation(summary = "Cria uma novo produto - segue boas práticas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "409", description = "Id já existe")
    })
    @PostMapping("/create-example2")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) {
        Product createdProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    /* ===================================================================================== */
    /* Exemplo 3: Versão Melhorada!!!
     * Este exemplo segue boas práticas REST. 
     * Por que específica a forma de retorno, no caso, retorna o status 201 (status(HttpStatus.CREATED)).
     * Ou seja, segui boas práticas REST e não é ambíguo em relação a criação de recursos.
     * Ainda gera o header Location que será adicionado à resposta HTTP.
     * http://localhost:8080/products/create-example3
    */
    @Operation(summary = "Cria uma novo produto - Versão melhorada utilizando Location")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "409", description = "Id já existe")
    })
    @PostMapping("/create-example3")
    public ResponseEntity<Product> addProductExample3(@RequestBody @Valid Product product) {
        Product createdProduct = productService.addProduct(product);
        URI location = URI.create("/products/" + createdProduct.getId());  // Cria a URI do novo recurso criado
        return ResponseEntity.created(location).body(createdProduct); // Retorna 201 Created com o corpo e o header Location
    }


    /* ===================================================================================== */
    /* Exemplo 4: Versão Melhorada sendo mais robusto!!!
     * Este exemplo segue boas práticas REST. 
     * Por que específica a forma de retorno, no caso, retorna o status 201 (status(HttpStatus.CREATED)).
     * Ou seja, segui boas práticas REST e não é ambíguo em relação a criação de recursos.
     * Ainda gera o header Location que será adicionado à resposta HTTP, porém mais robusto.
     * http://localhost:8080/products/create-example4
    */
    @Operation(summary = "Cria uma novo produto - Versão melhorada utilizando Location, mais robusto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "409", description = "Id já existe")
    })
    @PostMapping("/create-example4")
    public ResponseEntity<Product> addProductExample4(@RequestBody @Valid Product product) {
        Product createdProduct = productService.addProduct(product);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/products/{id}")
            .buildAndExpand(createdProduct.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdProduct);
    }


    /* ===================================================================================== */

    // http://localhost:8080/products/all
    @Operation(summary = "Lista todas os produtos")
    @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso")
    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }


    /* ===================================================================================== */


    // http://localhost:8080/products/name/CadeiraGamer
    @Operation(summary = "Busca um produto pelo nome")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<Product> findByNameProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.findByNameProduct(name));   
    }

    /** Exemplo caso não tenha tratamento de exceção no service - Utilizando O Optional: 
    
        @GetMapping("/name/{name}")
        public ResponseEntity<Product> findByNameProduct(@PathVariable String name) {
            Optional<Product> product = productService.findByNameProduct(name);
            return product.isPresent() ? ResponseEntity.ok(product.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    */


    /* ===================================================================================== */


    // http://localhost:8080/products/1
    @Operation(summary = "Busca um produto pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> findByIdProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findByIdProduct(id));
    }


    /* ===================================================================================== */


    // http://localhost:8080/products/update/1
    @Operation(summary = "Atualizar um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }
    

    /* ===================================================================================== */


    // http://localhost:8080/products/update-partial-price/1
    @Operation(summary = "Atualiza parcialmente o preço de um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado parcialmente"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/update-partial-price/{id}")
    public ResponseEntity<Product> updatePriceProduct(@PathVariable Long id, @RequestBody BigDecimal price) {
        return ResponseEntity.ok(productService.updatePriceProduct(id, price));
    }


    /* ===================================================================================== */


    // http://localhost:8080/products/update-patial-price-quantity/1
    @Operation(summary = "Atualiza parcialmente o preço e a quantidade de um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado parcialmente"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/update-patial-price-quantity/{id}")
    public ResponseEntity<Product> updatePriceAndQuantityProduct(@PathVariable Long id, @RequestParam BigDecimal price, @RequestParam int quantity) {
        return ResponseEntity.ok(productService.updatePriceAndQuantityProduct(id, price, quantity));
    }

    /*
     * ATENÇÃO: 
     * O Spring não aceita dois @RequestBody ao mesmo tempo. A requisição HTTP só pode ter um corpo JSON por vez, então só um parâmetro pode usar @RequestBody.
     * Aqui, o certo seria utilizar um DTO, mas como não estou utilizando. Uma alternativa é passar o "price" e "quantity" como parâmetros de query.
     * Isso funciona mais têm limitações se os dados forem mais complexos (ex: objetos aninhados, listas etc.).
    */


    /* ===================================================================================== */

    // http://localhost:8080/products/delete/1
    @Operation(summary = "Remove um produto pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) { 
        productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
            /** Ou pode fazer assim: return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); */
    }


    /* ===================================================================================== */

    // http://localhost:8080/products/delete/1

    @Operation(summary = "Atualiza parcialmente o estado do produto de um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado parcialmente"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/update-partial-productState/{id}")
    public ResponseEntity<Product> modifyProductState(@PathVariable Long id, @RequestBody ProductState productState) {
        return ResponseEntity.ok(productService.modifyProductState(id, productState));
    }


    /* ===================================================================================== */


    // http://localhost:8080/products/product-state/AVAILABLE
    @Operation(summary = "Busca produto pelo estado do produto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/product-state/{productState}")
    public ResponseEntity<List<Product>> findAllByProductState(@PathVariable ProductState productState) {
            return ResponseEntity.ok(productService.findAllByProductState(productState));
    }
    
}
