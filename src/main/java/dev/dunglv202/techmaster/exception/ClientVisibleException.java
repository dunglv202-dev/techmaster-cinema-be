package dev.dunglv202.techmaster.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientVisibleException extends RuntimeException {
    private final HttpStatus status;

    public ClientVisibleException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
