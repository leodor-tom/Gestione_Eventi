package TommasoEleodori.Gestione_Eventi.users.dto;

import TommasoEleodori.Gestione_Eventi.users.enums.Role;
import jakarta.validation.constraints.NotNull;

public record RoleDTO(@NotNull(message = "The role is manadatory")
                      Role role) {
}
