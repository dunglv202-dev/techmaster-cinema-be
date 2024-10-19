package dev.dunglv202.techmaster.exception;

import dev.dunglv202.techmaster.dto.ApiError;
import dev.dunglv202.techmaster.dto.ApiResp;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResp<?> validationError(MethodArgumentNotValidException e) {
        assert e.getBindingResult().getFieldError() != null;
        return ApiError.code(400).message(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResp<?> handleConstrainViolation(ConstraintViolationException e) {
        String err = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList().get(0);
        return ApiError.code(400).message(err);
    }

    @ExceptionHandler(ClientVisibleException.class)
    public ResponseEntity<?> visibleException(ClientVisibleException e) {
        return ResponseEntity.status(e.getStatus()).body(ApiError.code(e.getCode()).message(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResp<?> accessDenied(AccessDeniedException e) {
        log.info("Access denied", e);
        return ApiError.code(403).message("{access.denied}");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResp<?> noResourceFound(NoResourceFoundException e) {
        log.error("No resource found: {}", e.getResourcePath());
        return ApiError.code(404).message("{resource.not_found}");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResp<?> serverError(Exception e) {
        log.error("Unhandled server error", e);
        return ApiError.code(500).message("{server.internal_error}");
    }
}
