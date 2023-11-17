package TommasoEleodori.Gestione_Eventi.events.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventDTO(
        @NotEmpty(message = "The name is mandatory")
        @Size(min = 2, max = 225, message = "The name cannot contain less than 2 character and more than 225 characters")
        String name,
        @NotNull(message = "The date is mandatory")
        LocalDate date,
        @NotEmpty(message = "The venue is mandatory")
        String venue,
        @NotEmpty(message = "The description is mandatory")
        @Size(min = 5, max = 500, message = "The description cannot contain less than 5 characters and more than 500 characters")
        String descriptions,
        @NotNull(message = "The  number of seats available is mandatory")
        int seats
        
) {
}
