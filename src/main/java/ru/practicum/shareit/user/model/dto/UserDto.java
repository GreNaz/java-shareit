package ru.practicum.shareit.user.model.dto;

import lombok.*;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.error.validation.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {
    private final long id;
    @NotBlank(groups = Create.class, message = "Received user with empty name")
    private final String name;
    @Email(groups = {Create.class, Update.class}, message = "Incorrect email address")
    @NotEmpty(groups = Create.class, message = "Email not specified")
    @EqualsAndHashCode.Include
    private final String email;
}
