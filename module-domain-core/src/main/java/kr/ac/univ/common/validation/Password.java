package kr.ac.univ.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message();
    long min();
    long max();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}