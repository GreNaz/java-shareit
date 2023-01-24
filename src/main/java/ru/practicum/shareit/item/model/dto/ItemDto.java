package ru.practicum.shareit.item.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.error.validation.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemDto {
    private final long id;
    @NotBlank(groups = Create.class)
    private final String name;
    @NotBlank(groups = Create.class)
    private final String description;
    @NotNull(groups = Create.class)
    private final Boolean available;
}
