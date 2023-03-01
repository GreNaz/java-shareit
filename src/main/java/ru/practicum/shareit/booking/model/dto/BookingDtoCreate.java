package ru.practicum.shareit.booking.model.dto;

import lombok.*;
import ru.practicum.shareit.booking.model.validation.StartBeforeEndDateValid;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.error.validation.Update;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@StartBeforeEndDateValid(groups = {Create.class, Update.class})
public class BookingDtoCreate {
    private long id;
    @FutureOrPresent(groups = Create.class)
    private LocalDateTime start;
    @Future(groups = Create.class)
    private LocalDateTime end;
    private long itemId;
}
