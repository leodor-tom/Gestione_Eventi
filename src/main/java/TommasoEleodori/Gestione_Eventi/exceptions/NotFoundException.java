package TommasoEleodori.Gestione_Eventi.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Record whit id: " + id + " not found");
    }

    public NotFoundException(String email) {
        super("Record whit email: " + email + " not found");
    }
}
