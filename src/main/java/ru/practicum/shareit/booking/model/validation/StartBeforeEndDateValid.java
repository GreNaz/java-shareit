package ru.practicum.shareit.booking.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE_USE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CheckDateValidator.class)
public @interface StartBeforeEndDateValid {
    String message() default "Start must be before Ending, and mustn't null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
