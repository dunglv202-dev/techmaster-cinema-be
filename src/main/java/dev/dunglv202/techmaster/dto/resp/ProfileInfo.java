package dev.dunglv202.techmaster.dto.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileInfo {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String birthDate;
}
