package urmat.dev.megatask.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import urmat.dev.megatask.exceptions.AlreadyExistsException;
import urmat.dev.megatask.exceptions.BadRequestException;
import urmat.dev.megatask.exceptions.ForBiddenException;
import urmat.dev.megatask.exceptions.NotFoundException;
import urmat.dev.megatask.exceptions.response.ExceptionResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFound(NotFoundException notFoundException) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND).
                exceptionClassName(NotFoundException.class.getSimpleName()).
                message(notFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(ForBiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse forbidden(ForBiddenException forbiddenException) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN).
                exceptionClassName(ForBiddenException.class.getSimpleName()).
                message(forbiddenException.getMessage())
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse methodArgNotValidType(MethodArgumentTypeMismatchException methodArgumentNotValidTypeException) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST).
                exceptionClassName(methodArgumentNotValidTypeException.getClass().getSimpleName()).
                message(methodArgumentNotValidTypeException.getMessage())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequest(BadRequestException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST).
                exceptionClassName(e.getClass().getSimpleName()).
                message(e.getMessage())
                .build();
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ExceptionResponse methodArgNotValidType(AlreadyExistsException methodArgumentNotValidTypeException) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.ALREADY_REPORTED).
                exceptionClassName(methodArgumentNotValidTypeException.getClass().getSimpleName()).
                message(methodArgumentNotValidTypeException.getMessage())
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleBadCredentialsException(BadCredentialsException ex) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .exceptionClassName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleIllegalStateException(IllegalStateException ex) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .exceptionClassName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .exceptionClassName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }


}
