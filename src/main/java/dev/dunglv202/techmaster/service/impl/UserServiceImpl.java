package dev.dunglv202.techmaster.service.impl;

import dev.dunglv202.techmaster.dto.resp.ProfileInfo;
import dev.dunglv202.techmaster.entity.User;
import dev.dunglv202.techmaster.mapper.UserMapper;
import dev.dunglv202.techmaster.model.auth.AuthUser;
import dev.dunglv202.techmaster.repository.UserRepository;
import dev.dunglv202.techmaster.service.UserService;
import dev.dunglv202.techmaster.util.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final AuthHelper authHelper;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneOrEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        return new AuthUser(user);
    }

    @Override
    public ProfileInfo getMyInfo() {
        long userId = Optional.ofNullable(authHelper.getSignedUser()).orElseThrow().getId();
        User user = userRepository.findById(userId).orElseThrow();
        return UserMapper.INSTANCE.toProfileInfo(user);
    }
}
