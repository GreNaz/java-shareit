package ru.practicum.shareit.user.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {
    private long id;
    private String name;
    @EqualsAndHashCode.Include
    private String email;
}
