package TommasoEleodori.Gestione_Eventi.users.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(@NotEmpty(message = "The name is mandatory")
                            @Size(min = 2, max = 30, message = "The name cannot contain less than 2 character and more than 20 characters")
                            String name,
                            @NotEmpty(message = "The surname is mandatory")
                            @Size(min = 2, max = 40, message = "the surname cannot contain less than 2 characters and more than 40 characters")
                            String surname) {
}
