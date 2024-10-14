package dev.dunglv202.techmaster.dto.req;

import dev.dunglv202.techmaster.validator.Password;
import dev.dunglv202.techmaster.validator.Username;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credential {
    @NotBlank
    @Username
    private String username;

    @NotBlank
    @Password
    private String password;
}
