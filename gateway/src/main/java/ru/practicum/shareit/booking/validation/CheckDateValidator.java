package ru.practicum.shareit.booking.validation;

import ru.practicum.shareit.booking.dto.BookingDtoCreate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class CheckDateValidator implements ConstraintValidator<StartBeforeEndDateValid, BookingDtoCreate> {
    @Override
    public void initialize(StartBeforeEndDateValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(BookingDtoCreate bookingDtoCreate, ConstraintValidatorContext context) {
        LocalDateTime start = bookingDtoCreate.getStart();
        LocalDateTime end = bookingDtoCreate.getEnd();
        if (start == null || end == null) {
            return false;
        }
        return start.isBefore(end);
    }
}
