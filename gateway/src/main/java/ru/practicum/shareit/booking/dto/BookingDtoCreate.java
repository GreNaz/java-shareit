package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.Create;
import ru.practicum.shareit.Update;
import ru.practicum.shareit.booking.validation.StartBeforeEndDateValid;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
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
