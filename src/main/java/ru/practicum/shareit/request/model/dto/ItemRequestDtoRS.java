package ru.practicum.shareit.request.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.item.model.dto.ItemDto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemRequestDtoRS {
    private Long id;
    private Long requesterId;
    @Size(groups = Create.class, min = 1, max = 200)
    private String description;
    private LocalDateTime created;
    private List<ItemDto> items;
}
