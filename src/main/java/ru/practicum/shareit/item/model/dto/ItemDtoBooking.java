package ru.practicum.shareit.item.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.booking.model.dto.BookingDtoRQ;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoBooking {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingDtoRQ lastBooking;
    private BookingDtoRQ nextBooking;
    private List<CommentDto> comments;
}
