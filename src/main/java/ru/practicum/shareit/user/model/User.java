package ru.practicum.shareit.user.model;

import lombok.*;
import ru.practicum.shareit.error.validation.Create;

import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    private long id;
    @NotNull(groups = Create.class, message = "Received user with empty name")
    private String name;

    //    @Email(groups = Create.class, message = "Incorrect email address")
//    @NotNull(groups = Create.class, message = "Email not specified")
    @EqualsAndHashCode.Include
    private String email;
}
