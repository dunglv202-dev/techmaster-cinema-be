package dev.dunglv202.techmaster.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientVisibleException extends RuntimeException {
    private final HttpStatus status;
    private final int code;

    public ClientVisibleException(String message) {
        super(message);
        this.code = 400;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ClientVisibleException(HttpStatus status, int code, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
