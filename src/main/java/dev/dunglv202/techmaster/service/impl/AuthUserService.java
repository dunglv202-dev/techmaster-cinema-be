package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.entity.User;
import dev.dunglv202.techmaster.model.auth.AuthUser;
import dev.dunglv202.techmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneOrEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        return new AuthUser(user);
    }
}
