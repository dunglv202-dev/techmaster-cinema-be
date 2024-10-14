package dev.dunglv202.techmaster.validator;

import dev.dunglv202.techmaster.constant.ValidationPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;
        return ValidationPattern.PHONE.matcher(s).matches();
    }
}
