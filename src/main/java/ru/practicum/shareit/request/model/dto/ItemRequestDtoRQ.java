package ru.practicum.shareit.request.model.dto;

import lombok.*;
import ru.practicum.shareit.error.validation.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ItemRequestDtoRQ {
    private Long id;
    private Long requesterId;
    @NotBlank(groups = Create.class)
    @Size(groups = Create.class, min = 1, max = 200)
    private String description;
    private LocalDateTime created;
}