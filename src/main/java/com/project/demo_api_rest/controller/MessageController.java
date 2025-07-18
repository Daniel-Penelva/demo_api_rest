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
