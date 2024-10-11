package dev.dunglv202.techmaster.model.auth;

import dev.dunglv202.techmaster.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record AuthUser(User user) implements UserDetails {
    public static AuthUser forUserId(long id) {
        User usr = new User();
        usr.setId(id);
        return new AuthUser(usr);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getId().toString();
    }
}
