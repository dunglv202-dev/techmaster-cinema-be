package dev.dunglv202.techmaster.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResp<P> {
    private int code;
    private boolean error;
    private String message;
    private P payload;

    public static <P> ApiResp<P> code(int code) {
        return new ApiResp<P>().setCode(code);
    }

    public static <P> ApiResp<P> error() {
        return new ApiResp<P>().setError(true);
    }

    public ApiResp<P> message(String message) {
        return this.setMessage(message);
    }

    public ApiResp<P> payload(P payload) {
        return this.setPayload(payload);
    }
}
