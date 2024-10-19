package dev.dunglv202.techmaster.controller;

import dev.dunglv202.techmaster.dto.resp.ProfileInfo;
import dev.dunglv202.techmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ProfileInfo getMyInfo() {
        return userService.getMyInfo();
    }
}
