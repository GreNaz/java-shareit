package ru.practicum.shareit.booking.model.validation;

import ru.practicum.shareit.booking.model.dto.BookingDtoRQ;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class CheckDateValidator implements ConstraintValidator<StartBeforeEndDateValid, BookingDtoRQ> {
    @Override
    public void initialize(StartBeforeEndDateValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(BookingDtoRQ bookingDtoRQ, ConstraintValidatorContext context) {
        LocalDateTime start = bookingDtoRQ.getStart();
        LocalDateTime end = bookingDtoRQ.getEnd();
        if (start == null || end == null) {
            return false;
        }
        return start.isBefore(end);
    }
}
