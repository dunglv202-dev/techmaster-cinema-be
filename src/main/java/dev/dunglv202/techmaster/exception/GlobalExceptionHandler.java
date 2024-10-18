package dev.dunglv202.techmaster.exception;

import dev.dunglv202.techmaster.dto.ApiError;
import dev.dunglv202.techmaster.dto.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResp<?> serverError(Exception e) {
        log.error("Unhandled server error", e);
        return ApiError.code(500).message("{server.internal_error}");
    }
}
