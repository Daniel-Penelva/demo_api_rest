package com.project.demo_api_rest.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        log.warn("Erro de Requisição inválida: {}", ex.getMessage(), ex);  // WARN porque não é um erro crítico
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), "Requisição inválida", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.info("Recurso não encontrado: {}", ex.getMessage()); // INFO porque é uma situação esperada, não um erro crítico
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "Recurso não encontrado", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        log.info("Categoria não encontrada: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Categoria não encontrada", HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        log.info("Produto não encontrado: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Produto não encontrado", HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        
        String fields = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(","));

        log.info("Produto não encontrado: {}", fields); // INFO porque é uma situação esperada, não um erro crítico
        log.debug("Detalhes do erro: {}", ex.getMessage(), ex); // DEBUG para detalhes adicionais

        ErrorResponse errorResponse = new ErrorResponse("Solicitação Incorreta", HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        log.error("Erro inesperado: {}", ex.getMessage(), ex);  // ERROR porque é um erro crítico
        ErrorResponse errorResponse = new ErrorResponse("Erro inesperado", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        
    }

}

/** Anotação:
 * @RestControllerAdvice - Atua como um componente global de interceptação de exceções e tratamento de dados para controladores REST.
 *                         Combina @ControllerAdvice e @ResponseBody.
 * 
 * @ControllerAdvice - Define uma classe como um handler global para exceções lançadas em qualquer controller da aplicação, não apenas em uma classe específica.
 * 
 * @ExceptionHandler - Usada dentro de @ControllerAdvice ou do @RestControllerAdvice para indicar que um método trata exceções específicas ou suas subclasses.
*/
