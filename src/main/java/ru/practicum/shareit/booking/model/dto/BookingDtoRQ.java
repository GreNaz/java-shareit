package ru.practicum.shareit.booking.model.dto;

import lombok.*;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.error.validation.Update;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDtoRQ {
    @NotNull(groups = Update.class)
    private long id;
    @FutureOrPresent(groups = Create.class)
    private LocalDateTime start;
    @Future(groups = Create.class)
    private LocalDateTime end;
    @NotNull(groups = Create.class)
    private long itemId;
    private long bookerId;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
