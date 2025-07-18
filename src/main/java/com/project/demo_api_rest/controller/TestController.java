package com.project.demo_api_rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // Utilizando Swagger: http://localhost:8080/swagger-ui.html

    // http://localhost:8080/api/test/hello-world
    @GetMapping("/hello-world")
    public String helloWorld(){
        return "Hello World";
    }
    
}

/* Introdução:
 * @RestController - Esta anotação é usada para indicar que a classe é um controlador REST.
 * @RequestMapping - Esta anotação é usada para indicar a URL que o controlador irá responder.
 * 
 * Métodos Http:
 *  @GetMapping - Esta anotação é usada para indicar que o método irá responder a requisições GET. É usado para recuperar dados.
 *  @PostMapping - Esta anotação é usada para indicar que o método irá responder a requisições POST. É usado para criar dados.
 *  @PutMapping - Esta anotação é usada para indicar que o método irá responder a requisições PUT. É usado para atualizar dados.
 *  @PatchMapping - Esta anotação é usada para indicar que o método irá responder a requisições PATCH. É usado para atualizar dados parcialmente.
 *  @DeleteMapping - Esta anotação é usada para indicar que o método irá responder a requisições DELETE. É usado para excluir dados.
*/
