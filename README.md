# APIs REST

## 🧩 O que é uma API?

**API (Application Programming Interface)** é um conjunto de regras que permite que diferentes sistemas se comuniquem entre si. Imagine que é como o cardápio de um restaurante: você (cliente) faz um pedido (requisição), e a cozinha (servidor) prepara e entrega a comida (resposta). Você não precisa saber como a comida é feita, só precisa fazer o pedido certo.

---

## 🌐 O que é uma API REST?

**REST (REpresentational State Transfer)** é um estilo de arquitetura para construir APIs na web. Ele usa os métodos padrão do protocolo HTTP para interagir com os dados. As APIs RESTful são simples, escaláveis e amplamente utilizadas.

<p align="center">
  <img src=".\src\main\resources\static\img\api_rest.png" alt="API REST" width=800/>
</p>

---

## 🔧 Principais Características de uma API REST:

1. **Baseada em recursos**
   Cada coisa importante (como um usuário, produto, pedido) é representada como um *recurso*, geralmente acessado por uma URL.

   * Exemplo: `/usuarios`, `/produtos/5`

2. **Usar os métodos HTTP:**

   * `GET` – para **buscar** dados
   * `POST` – para **criar** dados
   * `PUT` – para **atualizar** dados (ou `PATCH`)
   * `DELETE` – para **remover** dados

<p align="center">
  <img src=".\src\main\resources\static\img\verbos_http.png" alt="Verbos HTTP" width=800/>
</p>

3. **Sem estado (Stateless)**
   Cada requisição é independente. O servidor não guarda informações de requisições anteriores.

4. **Respostas padronizadas em JSON**
   A maioria das APIs REST retorna dados em formato **JSON**.

---

## 🧪 Exemplo prático de uma API REST:

### Recurso: Usuários

| Ação          | Método HTTP | Endpoint      | Descrição                   |
| ------------- | ----------- | ------------- | --------------------------- |
| Listar todos  | GET         | `/usuarios`   | Retorna todos os usuários   |
| Buscar por ID | GET         | `/usuarios/1` | Retorna o usuário com ID 1  |
| Criar novo    | POST        | `/usuarios`   | Cria um novo usuário        |
| Atualizar     | PUT         | `/usuarios/1` | Atualiza o usuário com ID 1 |
| Atualizar     | PATC        | `/usuarios/1` | Atualiza itens dos dados do usuário com ID 1 |
| Deletar       | DELETE      | `/usuarios/1` | Remove o usuário com ID 1   |

---

## 🎯 Status Code:

* Usar **verbos HTTP corretos**.
* Usar URLs claras e semânticas: `/produtos/123`, não `/getProdutoPorId/123`.
* Usar **códigos de status HTTP** corretamente:

  * `200 OK` – sucesso
  * `201 Created` – criação bem-sucedida
  * `400 Bad Request` – erro do cliente
  * `404 Not Found` – recurso não encontrado
  * `500 Internal Server Error` – erro no servidor

<p align="center">
  <img src=".\src\main\resources\static\img\http_status_code.png" alt="Status Code" width=800/>
</p>

---

## 🧠 O que significa "idempotente"?

Um **método HTTP é idempotente** quando **executá-lo uma ou várias vezes tem o mesmo efeito no servidor**.
Ou seja: **repetir a requisição não altera o resultado** (além da primeira execução).

<p align="center">
  <img src=".\src\main\resources\static\img\idempotente_verbos_http.png" alt="Idempotente Verbos HTTP" width=800/>
</p>

---

### 📊 Idempotência nos principais verbos HTTP:

| Verbo HTTP | Idempotente? | Explicação                                                                                          |
| ---------- | ------------ | --------------------------------------------------------------------------------------------------- |
| `GET`      | ✅ Sim        | Buscar um recurso não altera o estado do servidor, mesmo se fizer mil vezes.                        |
| `POST`     | ❌ Não        | Cada chamada normalmente **cria** um novo recurso. Se repetir, cria outro.                          |
| `PUT`      | ✅ Sim        | Atualiza o recurso completamente. Se enviar os mesmos dados várias vezes, o resultado será o mesmo. |
| `PATCH`    | ⚠️ Depende   | Parcialmente idempotente. Depende da implementação.                                                 |
| `DELETE`   | ✅ Sim        | Deletar o mesmo recurso várias vezes continua tendo o mesmo efeito: ele foi excluído.               |

---

### 🧪 Exemplos práticos:

#### ✔️ `GET` (idempotente)

```http
GET /usuarios/1
```

→ Retorna o mesmo usuário toda vez. Não altera nada no servidor.

---

#### ❌ `POST` (não idempotente)

```http
POST /usuarios
Body: { "nome": "Maria" }
```

→ Cria um novo usuário. Se repetir, cria outro registro com o mesmo nome.

---

#### ✔️ `PUT` (idempotente)

```http
PUT /usuarios/1
Body: { "nome": "Maria" }
```

→ Atualiza o usuário com os mesmos dados. Repetir essa requisição não altera mais nada depois da primeira.

---

#### ✔️ `DELETE` (idempotente)

```http
DELETE /usuarios/1
```

→ Na primeira vez, deleta o usuário. Repetir depois não muda nada: o usuário já foi deletado.

---

### 📌 Por que a idempotência é importante?

* Facilita **retries** (tentativas automáticas em caso de falha).
* Garante **segurança** em operações que podem ser repetidas sem prejuízo.
* Melhora a previsibilidade e confiabilidade da API.

---

## 🧾 O que é JSON?

**JSON (JavaScript Object Notation)** é um **formato leve de troca de dados**, muito usado em APIs REST e aplicações web para enviar e receber informações entre cliente e servidor.

Ele é:

* Legível por humanos
* Baseado em texto
* Inspirado na sintaxe de objetos do JavaScript
* Independente de linguagem (pode ser usado em Java, Python, C#, etc.)

---

### 🔤 Estrutura básica do JSON

Um JSON é formado por **pares de chave e valor**, como em um dicionário ou objeto.

```json
{
  "nome": "Maria",
  "idade": 25,
  "email": "maria@example.com",
  "ativo": true
}
```

---

### 📚 Tipos de dados suportados:

| Tipo    | Exemplo                          |
| ------- | -------------------------------- |
| String  | `"nome": "João"`                 |
| Number  | `"idade": 30`                    |
| Boolean | `"ativo": true`                  |
| Array   | `"cursos": ["Java", "Python"]`   |
| Objeto  | `"endereco": { "cidade": "SP" }` |
| null    | `"telefone": null`               |

---

### 🧪 Exemplo real de JSON em API:

#### Requisição de criação de usuário (`POST /usuarios`):

```json
{
  "nome": "Carlos",
  "idade": 28,
  "email": "carlos@email.com",
  "roles": ["admin", "editor"],
  "endereco": {
    "rua": "Av. Brasil",
    "numero": 123,
    "cidade": "São Paulo"
  }
}
```

---

### 📤 Quando se usa JSON?

* Enviar dados para o back-end: `POST`, `PUT`, `PATCH`
* Receber dados do back-end: `GET`
* Armazenar configurações ou dados (ex: arquivos `.json` em projetos)

---

## 📦 Exemplo de JSON como resposta de uma API:

```json
{
  "status": "sucesso",
  "dados": {
    "id": 1,
    "nome": "Ana",
    "email": "ana@email.com"
  }
}
```

---

### ⚠️ Dicas e cuidados

* As chaves e strings **devem estar entre aspas duplas (`"`)**.
* Não pode usar **vírgula depois do último item**.
* JSON ≠ JavaScript (parecem, mas não são iguais).

---

## 📌 Na prática - exemplo simples

### 📦 Classe **`Message`**

```java
package com.project.demo_api_rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private int id;
    private String content;
    private String tag;

}
```
---

### 📦 Classe **`MessageController`**

```java
package com.project.demo_api_rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo_api_rest.model.Message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private List<Message> messages = new ArrayList<>();

    public MessageController() {
        messages.add(new Message(1, "Teste mensagem 1.", "msg1"));
        messages.add(new Message(2, "Teste mensagem 2.", "msg2"));
        messages.add(new Message(3, "Teste mensagem 3.", "msg3"));
    }

    // Utilizando Swagger: http://localhost:8080/swagger-ui.html

    // Obter todas as mensagens - http://localhost:8080/api/message/all
    @Operation(summary = "Lista todas as mensagens")
    @ApiResponse(responseCode = "200", description = "Mensagens listadas com sucesso")
    @GetMapping("/all")
    public List<Message> findAllMessages() {
        if(messages.isEmpty()) {
            System.out.println("Lista de mensagem vazia!");
        }
        return messages;
    }


    // Obter mensagem por Id - http://localhost:8080/api/message/by/1
    @Operation(summary = "Busca uma mensagem pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mensagem encontrada"),
        @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    @GetMapping("/by/{id}")
    public Message findByMessageId(@PathVariable int id){
        Optional<Message> message = messages.stream()
            .filter(msg -> msg.getId() == id)
            .findFirst();
        return message.orElse(null);
    }


    // Criar mensagem - http://localhost:8080/api/message/created
    /*
     * JSON:
     * {
     *    "id": 4,
     *    "content": "Teste mensagem 4.",
     *    "tag": "msg4"
     * }
    */
    @Operation(summary = "Cria uma nova mensagem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Mensagem criada com sucesso"),
        @ApiResponse(responseCode = "409", description = "Id já existe")
    })
    @PostMapping("/created")
    public Message createdMessage(@RequestBody Message message) {

        boolean idExists = messages.stream()
                .anyMatch(msg -> msg.getId() == message.getId());

        if (idExists) {
            throw new IllegalArgumentException("Já existe uma mensagem com o Id: " + message.getId());
        }

        messages.add(message);
        return message;
    }


    // Deletar mensagem por id - http://localhost:8080/api/message/delete/1
    @Operation(summary = "Remove uma mensagem pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    @DeleteMapping("/delete/{id}")
    public void deleteMessage(@PathVariable int id) {

        boolean removed = messages.removeIf(m -> m.getId() == id);

        if (removed) {
            System.out.println("Removida.");
        } else {
            System.out.println("Não encontrada!");
        }
    }


    // Atualizar mensagem por id - http://localhost:8080/api/message/update/1
    @Operation(summary = "Atualizar uma mensagem existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mensagem atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    @PutMapping("/update/{id}")
    public void updateMessage(@PathVariable int id, @RequestBody Message newMessage) {

        for(int i=0; i<messages.size(); i++) {
            if (messages.get(i).getId() == id) {
                messages.set(i, newMessage);
                return;
            }

            System.out.println("Mensagem com ID" + id + " não encontrada!");
        }
    }


    // Atualizar parcialmente mensagem por id - http://localhost:8080/api/message/update-partial/1
    @Operation(summary = "Atualiza parcialmente uma mensagem existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mensagem atualizada parcialmente"),
        @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    @PatchMapping("/update-partial/{id}")
    public void patchMessage(@PathVariable int id, @RequestBody Message parcialMessage) {

        for(Message m : messages) {
            if (m.getId() == id) {
                if (parcialMessage.getContent() != null) m.setContent(parcialMessage.getContent());
                if (parcialMessage.getTag() != null) m.setTag(parcialMessage.getTag());
                return;
            }
        }
        System.out.println("Mensagem com ID " + id + " não encontrada.");
    }
    
}
```
---

### Acesso

✔️ Acesse - Utilizando [Swagger-ui](http://localhost:8080/swagger-ui/index.html)

✔️ Acesse - Documentação [Swagger-ui](https://springdoc.org/#getting-started)

---

### Depêndencia Swagger-ui

✔️ Dependência Swagger-ui:
  - Para a integração entre spring-boot e swagger-ui, adicione a biblioteca à lista de dependências do projeto (nenhuma configuração adicional é necessária)
```xml
  <!-- Site OpenAPI - Swagger: https://springdoc.org/#getting-started -->
	<dependency>
		<groupId>org.springdoc</groupId>
		<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
		<version>2.8.9</version>
  </dependency>
```

---

## ✅ ResponseEntity

**ResponseEntity** é uma classe do Spring Framework que representa toda a resposta HTTP em controladores REST, permitindo o controle sobre o status HTTP, os cabeçalhos e o corpo da resposta enviada ao cliente.

### Principais características do ResponseEntity em controladores REST:

- **Controla o código de status HTTP**: permite definir explicitamente códigos como 200 OK, 201 Created, 404 Not Found, entre outros, possibilitando diferentes respostas conforme situação da requisição.

- **Permite customizar os cabeçalhos HTTP**: É possível incluir headers personalizados na resposta, como informações adicionais para o cliente, sem limitar-se ao corpo da mensagem.

- **Define o corpo da resposta**: O ResponseEntity é genérico e aceita qualquer tipo de conteúdo (String, JSON, objetos, etc.), que é enviado no corpo da resposta.

- **Flexibilidade para respostas mais completas**: Enquanto um @RestController pode retornar diretamente o corpo (exemplo: um objeto ou String) e usar @ResponseStatus para definir um status fixo, o ResponseEntity é utilizado quando se deseja retornar status, cabeçalhos e corpo de forma dinâmica e customizável.

---

### Exemplo básico de uso:

```java
@GetMapping("/exemplo")
public ResponseEntity exemplo() {
    return ResponseEntity.ok()
        .header("X-Custom-Header", "valor")
        .body("Olá Mundo!");
}
```

🔎 Neste exemplo, além de retornar o corpo com uma String, é configurado um header customizado, e o código de status HTTP padrão 200 OK é utilizado.

---

### Quando usar ResponseEntity?

- Quando precisar customizar o código de status HTTP dinamicamente (ex: erro 400, criado 201, não encontrado 404).
- Quando precisar adicionar cabeçalhos HTTP personalizados na resposta.
- Quando desejar retornar respostas que não sejam apenas o corpo simples, oferecendo um controle completo da resposta para o consumidor da API REST.

---

### Resumo

| Aspecto          | Retorno simples (@RestController)                 | ResponseEntity                                    |
|------------------|--------------------------------------------------|--------------------------------------------------|
| Corpo da resposta| Simples (objeto, String)                         | Simples ou complexo (qualquer tipo)               |
| Código HTTP       | Fixo, geralmente 200 OK por padrão                | Totalmente customizável (200, 201, 404, etc.)    |
| Cabeçalhos        | Não personalizado diretamente                     | Pode personalizar headers                         |
| Uso típico        | Respostas simples e estáticas                      | Respostas com necessidade de controle fino       |

---

## 📌 Na prática - exemplo utilizando `ResponseEntity`

  - Este exemplo vai ser utilizado para demonstrar a utilização do `ResponseEntity` em um controlador REST do Spring Boot.
  - O objetivo é criar um controlador que, ao receber uma requisição HTTP GET, POST, PUT ou DELETE, retorne uma resposta com um código de status HTTP personalizado e um corpo dada a situação da requisição.
  - Vai ser utilizado o try...catch() para lidar com exceções e retornar uma resposta com código de status HTTP para caso onde ocorra algum erro.

---

### 📦 Entity **`Product`**

```java
package com.project.demo_api_rest.model;

import java.math.BigDecimal;

import com.project.demo_api_rest.enums.ProductState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT_TBL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal price;

    @Column(nullable = false)
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private int quantity;

    @NotNull(message = "O estado do produto é obrigatório")
    @Enumerated(EnumType.STRING)  // @EnumType - Define o tipo de enumeração. No caso EnumType.String define que o enum será armazenado como string.
    @Column(name = "product_state", nullable = false)
    private ProductState productState;

}
```

---

### 📦 Enum **`Product`**

```java
public enum ProductState {
    AVAILABLE, UNAVAILABLE;

    // DISPONIVEL (AVAILABLE) - o produto está disponível para compra
    // INDISPONÍVEL (UNAVAILABLE) - o produto não está disponível para compra
}
```

---

### 📦 Repository **`ProductRepository`**

```java
package com.project.demo_api_rest.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.demo_api_rest.enums.ProductState;
import com.project.demo_api_rest.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    List<Product> findByProductState(ProductState productState);
}
```

---

### 📦 Exception Personalized **`ProductService`**

```java
package com.project.demo_api_rest.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        super("Produto não encontrado com ID: " + id);
    }
    
}
```

---

### 📦 Service **`ProductService`**

```java
package com.project.demo_api_rest.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.project.demo_api_rest.enums.ProductState;
import com.project.demo_api_rest.model.Product;

public interface ProductService {

    Product addProduct(Product product);

    List<Product> findAllProducts();

    Product findByNameProduct(String name);

    Product findByIdProduct(Long id);

    Product updateProduct(Long id, Product product);

    Product updatePriceProduct(Long id, BigDecimal price);

    Product updatePriceAndQuantityProduct(Long id, BigDecimal price, int quantity);

    void deleteProduct(Long id);

    Product modifyProductState(Long id, ProductState productState);

    List<Product> findAllByProductState(ProductState productState);
}
```

---

### 📦 Service Implements **`ProductServiceImpl`**

```java
package com.project.demo_api_rest.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.project.demo_api_rest.enums.ProductState;
import com.project.demo_api_rest.exception.ProductNotFoundException;
import com.project.demo_api_rest.model.Product;
import com.project.demo_api_rest.repository.ProductRepository;
import com.project.demo_api_rest.service.ProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplements implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        System.out.println("Produto sendo salvo na service: " + product); // Adicione este log
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findByNameProduct(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Produto com o nome " + name + " não encontrado"));
    }

    @Override
    public Product findByIdProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product productBD = findByIdOrThrow(id);

        productBD.setName(product.getName());
        productBD.setDescription(product.getDescription());
        productBD.setPrice(product.getPrice());
        productBD.setQuantity(product.getQuantity());
        productBD.setProductState(product.getProductState());

        return productRepository.save(productBD);
    }

    @Override
    public Product updatePriceProduct(Long id, BigDecimal price) {
        Product productBD = findByIdOrThrow(id);

        productBD.setPrice(price);
        return productRepository.save(productBD);
    }

    @Override
    public Product updatePriceAndQuantityProduct(Long id, BigDecimal price, int quantity) {
        Product productBD = findByIdOrThrow(id);

        productBD.setPrice(price);
        productBD.setQuantity(quantity);
        return productRepository.save(productBD);
    }

    @Override
    public void deleteProduct(Long id) {
        findByIdOrThrow(id);
        productRepository.deleteById(id);

    }

    @Override
    public Product modifyProductState(Long id, ProductState productState) {
        Product productBD = findByIdOrThrow(id);

        productBD.setProductState(productState);
        return productRepository.save(productBD);
    }

    @Override
    public List<Product> findAllByProductState(ProductState productState) {
        return productRepository.findByProductState(productState);
    }

    // Método que busca o id para verificar se o produto existe ou não existe.
    private Product findByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
```

---

### 📦 Controller **`ProductController`**

```java
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
    public ResponseEntity<?> findByNameProduct(@PathVariable String name) {
        try {
            return ResponseEntity.ok(productService.findByNameProduct(name));
        } catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar o produto por nome: " + exception.getMessage());
        }
        
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
    public ResponseEntity<?> findByIdProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.findByIdProduct(id));
        } catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar o produto por ID: " + exception.getMessage());
        }
        
    }


    /* ===================================================================================== */


    // http://localhost:8080/products/update/1
    @Operation(summary = "Atualizar um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        }catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atuaizar o produto: " + exception.getMessage());
        }
        
    }
    

    /* ===================================================================================== */


    // http://localhost:8080/products/update-partial-price/1
    @Operation(summary = "Atualiza parcialmente o preço de um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado parcialmente"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/update-partial-price/{id}")
    public ResponseEntity<?> updatePriceProduct(@PathVariable Long id, @RequestBody BigDecimal price) {
        try{
            return ResponseEntity.ok(productService.updatePriceProduct(id, price));
        }catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atuaizar parcialmente o produto: " + exception.getMessage());
        }
        
    }


    /* ===================================================================================== */


    // http://localhost:8080/products/update-patial-price-quantity/1
    @Operation(summary = "Atualiza parcialmente o preço e a quantidade de um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado parcialmente"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/update-patial-price-quantity/{id}")
    public ResponseEntity<?> updatePriceAndQuantityProduct(@PathVariable Long id, @RequestParam BigDecimal price, @RequestParam int quantity) {
        try {
            return ResponseEntity.ok(productService.updatePriceAndQuantityProduct(id, price, quantity));
        }catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atuaizar parcialmente o produto: " + exception.getMessage());
        }
        
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
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) { 
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
            /** Ou pode fazer assim: return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); */ 
        }catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar produto: " + exception.getMessage());
        }
    }


    /* ===================================================================================== */

    // http://localhost:8080/products/delete/1

    @Operation(summary = "Atualiza parcialmente o estado do produto de um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado parcialmente"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/update-partial-productState/{id}")
    public ResponseEntity<?> modifyProductState(@PathVariable Long id, @RequestBody ProductState productState) {
        try {
            return ResponseEntity.ok(productService.modifyProductState(id, productState));
        } catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao alterar estado do produto: " + exception.getMessage());
        }
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
            List<Product> products = productService.findAllByProductState(productState);

            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
            }
            return ResponseEntity.ok(products);
    }
    
}
```

---

## ⚠️ Bom Saber - Boas Práticas do REST

Analisar a diferença entre os dois exemplos e qual é mais apropriado para uso em produção.

### ✅ Diferença entre os dois:

#### (1) **Exemplo 1**

```java
@PostMapping("/create")
public ResponseEntity<Product> addProductOtherExample(@RequestBody Product product) {
    return ResponseEntity.ok(productService.addProduct(product));
}
```

* **HTTP Status** retornado: `200 OK`
* Semântica: indica que a requisição foi bem-sucedida e **o recurso já existia ou foi processado normalmente**.
* Pode causar **confusão semântica** em operações de criação.

---

#### (2) **Exemplo 2**

```java
@PostMapping("/create")
public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    Product createdProduct = productService.addProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
}
```

* **HTTP Status** retornado: `201 Created`
* Semântica: indica que o recurso foi **criado com sucesso**, o que é o mais adequado para requisições **POST de criação**.
* Está em conformidade com o padrão **HTTP/REST**.

---

### 🏭 Qual usar em produção?

**✅ A forma correta e recomendada em produção é a (2)**, por esses motivos:

| Critério                                              | Forma (1) `ok(...)`            | Forma (2) `status(HttpStatus.CREATED)` |
| ----------------------------------------------------- | ------------------------------ | -------------------------------------- |
| Retorna o status correto (201)                        | ❌ Não (200 OK)                 | ✅ Sim                                  |
| Segue boas práticas REST                              | ❌ Não                          | ✅ Sim                                  |
| Indica claramente criação de recurso                  | ❌ Ambíguo                      | ✅ Sim                                  |
| Aderência a ferramentas/documentações Swagger/OpenAPI | ✅ Funciona, mas pode confundir | ✅ Recomendado                          |

---

### (3) **Exemplo 3** 🧠 Dica adicional para melhorar ainda mais

Ou se quiser seguir o padrão REST até o fim pode incluir o header `Location` com o URI do novo recurso criado:

```java
@PostMapping("/create")
public ResponseEntity<Product> addProductExample3(@RequestBody Product product) {
    Product createdProduct = productService.addProduct(product);
    URI location = URI.create("/products/" + createdProduct.getId());  // Cria a URI do novo recurso criado
    return ResponseEntity.created(location).body(createdProduct); // Retorna 201 Created com o corpo e o header Location
}
```

🔎 O que muda:
  - ResponseEntity.created(location) automaticamente seta o status 201 Created.

  - Retorna o produto criado no corpo da resposta.

  - O header Location será adicionado à resposta HTTP. Exemplo:

```http
HTTP/1.1 201 Created
Location: /products/42
Content-Type: application/json

{
  "id": 42,
  "name": "Produto X",
  "price": 99.99
}
```

---

### (4) **Exemplo 4** 🔧 Melhorias opcionais:

A URI `"/products/" + createdProduct.getId()` está **hardcoded**. Isso pode quebrar o path ao mudar no futuro. Uma forma mais segura seria usar:

```java
URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .replacePath("/products/{id}")
        .buildAndExpand(createdProduct.getId())
        .toUri();
```

Ou, se quiser manter o `/create`, mas apontar para o novo recurso mesmo assim:

```java
URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/products/{id}")
        .buildAndExpand(createdProduct.getId())
        .toUri();
```

Essa abordagem constrói dinamicamente com base na URL real da aplicação (útil se o contexto mudar, como em `/api/v1/...`).

---

#### ✅ Resultado final com a melhoria:

```java
@PostMapping("/create")
public ResponseEntity<Product> addProductExample4(@RequestBody Product product) {
    Product createdProduct = productService.addProduct(product);

    URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/products/{id}")
        .buildAndExpand(createdProduct.getId())
        .toUri();

    return ResponseEntity.created(location).body(createdProduct);
}
```

---

### ✅ Conclusão

**A forma (2) com `HttpStatus.CREATED`** em endpoints de criação (`POST`). Isso torna sua API mais semântica, aderente ao padrão REST e melhora a clareza para consumidores da API e ferramentas como Swagger.

---

## Feito por: **`Daniel Penelva`**
