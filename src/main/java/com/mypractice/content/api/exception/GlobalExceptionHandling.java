package com.mypractice.content.api.exception;

import com.mypractice.content.api.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *   final Map<String, String> errors =
 *                 e.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(error -> ((FieldError) error).getField(), DefaultMessageSourceResolvable::getDefaultMessage, (a, b) -> b));
 *         return ResponseEntity.badRequest().body(errors);*
 */
@ControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<ErrorDto>> handleException(WebExchangeBindException e) {
       return ResponseEntity.badRequest().body( Mono.just(ErrorDto.builder().code("field_validation_error").
                details(extractErrorDetails(e)).build()));

    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Mono<ErrorDto>> notFoundException(NotFoundException e) {
        return ResponseEntity.badRequest().body( Mono.just(ErrorDto.builder().code("resource_not_found").
                details(extractErrorDetails(e))
                .description(HttpStatus.NOT_FOUND.name()).build()));

    }

    private List<ErrorDto.ErrorDetail> extractErrorDetails(NotFoundException exception) {
        return Arrays.asList(new ErrorDto.ErrorDetail(HttpStatus.NOT_FOUND.name(), exception.getMessage()));
    }

    private List<ErrorDto.ErrorDetail> extractErrorDetails(WebExchangeBindException exception) {
        return exception.getFieldErrors().stream()
                .map(this::toErrorDetail).collect(Collectors.toList());
    }
    private ErrorDto.ErrorDetail toErrorDetail(FieldError fieldError) {
        return new ErrorDto.ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage());
    }

}
