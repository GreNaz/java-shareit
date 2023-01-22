package ru.practicum.shareit.user.model.dto;

import lombok.*;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.error.validation.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {
    private long id;
    @NotBlank(groups = Create.class, message = "Received user with empty name")
    private String name;
    @Email(groups = {Create.class, Update.class}, message = "Incorrect email address")
    @NotEmpty(groups = Create.class, message = "Email not specified")
    @EqualsAndHashCode.Include
    private String email;
}
