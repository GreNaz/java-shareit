package ru.practicum.shareit.user.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    private long id;
    private String name;
    @EqualsAndHashCode.Include
    private String email;
}
