package ru.practicum.shareit.item.model.dto;

import lombok.*;
import ru.practicum.shareit.booking.model.dto.BookingDtoResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemDtoBooking {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingDtoResponse lastBooking;
    private BookingDtoResponse nextBooking;
    private List<CommentDto> comments;
}
