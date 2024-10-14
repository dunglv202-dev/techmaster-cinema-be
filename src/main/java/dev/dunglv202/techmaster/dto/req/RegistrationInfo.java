package dev.dunglv202.techmaster.dto.req;

import dev.dunglv202.techmaster.validator.Email;
import dev.dunglv202.techmaster.validator.Password;
import dev.dunglv202.techmaster.validator.Phone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegistrationInfo {
    @NotBlank
    private String firstName;

    private String lastName;

    @Past
    @NotNull
    private LocalDate birthDate;

    @Email
    private String email;

    @Phone
    @NotNull
    private String phone;

    @Password
    private String password;
}
