package dev.dunglv202.techmaster.util;

import dev.dunglv202.techmaster.dto.resp.AuthResult;
import dev.dunglv202.techmaster.entity.User;
import dev.dunglv202.techmaster.model.auth.AuthUser;
import dev.dunglv202.techmaster.model.prop.AuthProperties;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthHelper {
    public static final String ACCESS_TOKEN_COOKIE = "accessToken";
    private static final String ACCESS_TOKEN_COOKIE_PATH = "/";
    public static final String REFRESH_TOKEN_COOKIE = "refreshToken";
    private static final String REFRESH_TOKEN_COOKIE_PATH = "/api/auth/refresh";
    public static final String JWT_TOKEN_TYPE_KEY = "type";
    public static final String JWT_TOKEN_TYPE_REFRESH = "REFRESH";

    private final AuthProperties authProperties;
    private final JwtProvider jwtProvider;

    @Nullable
    public User getSignedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return null;
        return ((AuthUser) authentication.getPrincipal()).user();
    }

    public void addAuthCookies(HttpServletResponse response, AuthResult authResult) {
        Cookie accessCookie = makeHttpCookie(
            ACCESS_TOKEN_COOKIE,
            authResult.getAccessToken(),
            ACCESS_TOKEN_COOKIE_PATH,
            authProperties.getTokenLifetime()
        );
        response.addCookie(accessCookie);

        Cookie refreshCookie = makeHttpCookie(
            REFRESH_TOKEN_COOKIE,
            authResult.getRefreshToken(),
            REFRESH_TOKEN_COOKIE_PATH,
            authProperties.getRefreshLifetime()
        );
        response.addCookie(refreshCookie);
    }

    public void removeAuthCookies(HttpServletResponse response) {
        response.addCookie(makeHttpCookie(ACCESS_TOKEN_COOKIE, null, ACCESS_TOKEN_COOKIE_PATH, Duration.ZERO));
        response.addCookie(makeHttpCookie(REFRESH_TOKEN_COOKIE, null, REFRESH_TOKEN_COOKIE_PATH, Duration.ZERO));
    }

    private Cookie makeHttpCookie(String type, String value, String path, Duration age) {
        Cookie cookie = new Cookie(type, value);
        cookie.setPath(path);
        cookie.setMaxAge((int) age.toMinutes());
        cookie.setHttpOnly(true);

        return cookie;
    }

    public AuthResult buildAuthResult(User user) {
        return AuthResult.builder()
            .accessToken(generateAccessToken(user))
            .refreshToken(generateRefreshToken(user))
            .build();
    }

    public String generateAccessToken(User user) {
        return jwtProvider.generateToken(
            user.getId().toString(),
            authProperties.getTokenLifetime(),
            Map.of()
        );
    }

    public String generateRefreshToken(User user) {
        return jwtProvider.generateToken(
            user.getId().toString(),
            authProperties.getRefreshLifetime(),
            Map.of(JWT_TOKEN_TYPE_KEY, JWT_TOKEN_TYPE_REFRESH)
        );
    }
}
