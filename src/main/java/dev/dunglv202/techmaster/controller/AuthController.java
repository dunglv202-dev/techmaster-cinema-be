package dev.dunglv202.techmaster.controller;

import dev.dunglv202.techmaster.dto.req.Credential;
import dev.dunglv202.techmaster.dto.req.RegistrationInfo;
import dev.dunglv202.techmaster.dto.resp.AuthResult;
import dev.dunglv202.techmaster.service.AuthService;
import dev.dunglv202.techmaster.util.AuthHelper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static dev.dunglv202.techmaster.util.AuthHelper.REFRESH_TOKEN_COOKIE;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthHelper authHelper;

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegistrationInfo registrationInfo) {
        authService.register(registrationInfo);
    }

    @PostMapping("/login")
    public AuthResult login(@Valid @RequestBody Credential credential, HttpServletResponse response) {
        AuthResult authResult = authService.login(credential);
        authHelper.addAuthCookies(response, authResult);
        return authResult;
    }

    @PostMapping("/refresh")
    public AuthResult refresh(@CookieValue(REFRESH_TOKEN_COOKIE) String refreshToken, HttpServletResponse response) {
        AuthResult authResult = authService.refresh(refreshToken);
        authHelper.addAuthCookies(response, authResult);
        return authResult;
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        authHelper.removeAuthCookies(response);
    }
}
