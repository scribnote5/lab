package kr.ac.univ.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    long min;
    long max;

    @Override
    public void initialize(Password password) {
        min = password.min();
        max = password.max();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext cxt) {
        return str.length() == 0 || str.length() <= max && str.length() >= min;
    }
}