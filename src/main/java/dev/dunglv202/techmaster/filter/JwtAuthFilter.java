package dev.dunglv202.techmaster.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dunglv202.techmaster.dto.ApiError;
import dev.dunglv202.techmaster.dto.ApiResp;
import dev.dunglv202.techmaster.model.auth.AuthUser;
import dev.dunglv202.techmaster.util.AuthHelper;
import dev.dunglv202.techmaster.util.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
        @Nonnull HttpServletRequest request,
        @Nonnull HttpServletResponse response,
        @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getCookies() == null || "/api/auth/refresh".equals(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        // check jwt token
        Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies())
            .filter(cookie -> AuthHelper.ACCESS_TOKEN_COOKIE.equals(cookie.getName()))
            .findFirst();
        if (tokenCookie.isPresent()) {
            try {
                validateAccessToken(tokenCookie.get().getValue());
            } catch (ExpiredJwtException e) {
                String resp = new ObjectMapper().writeValueAsString(ApiResp.code(1));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(resp);
                return;
            } catch (JwtException e) {
                String resp = new ObjectMapper().writeValueAsString(ApiError.code(1));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(resp);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateAccessToken(String accessToken) {
        Claims claims = jwtProvider.verifyToken(accessToken);

        // set authentication context
        Authentication authentication = new PreAuthenticatedAuthenticationToken(
            AuthUser.forUserId(Long.parseLong(claims.getSubject())),
            null,
            List.of()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
