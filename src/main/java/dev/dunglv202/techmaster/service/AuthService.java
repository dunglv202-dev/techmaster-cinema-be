package dev.dunglv202.techmaster.service;

import dev.dunglv202.techmaster.dto.req.Credential;
import dev.dunglv202.techmaster.dto.req.RegistrationInfo;
import dev.dunglv202.techmaster.dto.resp.AuthResult;

public interface AuthService {
    AuthResult login(Credential credential);

    AuthResult refresh(String refreshToken);

    void register(RegistrationInfo registrationInfo);
}
