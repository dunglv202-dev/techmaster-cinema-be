package dev.dunglv202.techmaster.dto.resp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResult {
    private String accessToken;
    private String refreshToken;
}
