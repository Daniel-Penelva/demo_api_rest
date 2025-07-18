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

✔️ Acesse - Utilizando [Swagger-ui](http://localhost:8080/swagger-ui/index.html)

✔️ Acesse - Documentação [Swagger-ui](https://springdoc.org/#getting-started)

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

## Feito por: **`Daniel Penelva`**
