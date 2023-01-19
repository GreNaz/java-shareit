package ru.practicum.shareit.user.model.dto;

import lombok.*;
import ru.practicum.shareit.error.validation.Create;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {
    private long id;
    @NotNull(groups = Create.class, message = "Received user with empty name")
    private String name;

    //    @Email(groups = Create.class, message = "Incorrect email address")
    //    @NotNull(groups = Create.class, message = "Email not specified")
    @EqualsAndHashCode.Include
    private String email;
}
