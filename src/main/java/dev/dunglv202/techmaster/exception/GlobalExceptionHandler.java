package dev.dunglv202.techmaster.exception;

import dev.dunglv202.techmaster.dto.ApiError;
import dev.dunglv202.techmaster.dto.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResp<?> serverError(Exception e) {
        log.error("Unhandled server error", e);
        return ApiError.code(500).message("{server.internal_error}");
    }
}
