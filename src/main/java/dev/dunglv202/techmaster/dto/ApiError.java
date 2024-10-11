package dev.dunglv202.techmaster.dto;

public class ApiError<P> extends ApiResp<P> {
    public static <P> ApiResp<P> code(int code) {
        return ApiResp.<P>error().setCode(code);
    }
}
