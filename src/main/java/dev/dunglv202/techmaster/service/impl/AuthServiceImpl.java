package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.dto.req.Credential;
import dev.dunglv202.techmaster.dto.req.RegistrationInfo;
import dev.dunglv202.techmaster.dto.resp.AuthResult;
import dev.dunglv202.techmaster.entity.User;
import dev.dunglv202.techmaster.exception.ClientVisibleException;
import dev.dunglv202.techmaster.mapper.UserMapper;
import dev.dunglv202.techmaster.model.auth.AuthUser;
import dev.dunglv202.techmaster.repository.UserRepository;
import dev.dunglv202.techmaster.service.AuthService;
import dev.dunglv202.techmaster.util.AuthHelper;
import dev.dunglv202.techmaster.util.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static dev.dunglv202.techmaster.util.AuthHelper.JWT_TOKEN_TYPE_KEY;
import static dev.dunglv202.techmaster.util.AuthHelper.JWT_TOKEN_TYPE_REFRESH;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthHelper authHelper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResult login(Credential credential) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            credential.getUsername(),
            credential.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authToken);
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return authHelper.buildAuthResult(authUser.user());
    }

    @Override
    public AuthResult refresh(String refreshToken) {
        Claims claims = jwtProvider.verifyToken(refreshToken);
        User user = userRepository.findById(Long.parseLong(claims.getSubject())).orElseThrow();

        if (!JWT_TOKEN_TYPE_REFRESH.equals(claims.get(JWT_TOKEN_TYPE_KEY))) {
            // TODO: this should be client visible
            throw new RuntimeException("{jwt.bad_malformed}");
        }

        return authHelper.buildAuthResult(user);
    }

    @Override
    public void register(RegistrationInfo registrationInfo) {
        User user = UserMapper.INSTANCE.toUser(registrationInfo);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsByPhone(user.getPhone())) {
            throw new ClientVisibleException("{phone.existed}");
        }

        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new ClientVisibleException("{email.existed}");
        }

        userRepository.save(user);
    }
}
