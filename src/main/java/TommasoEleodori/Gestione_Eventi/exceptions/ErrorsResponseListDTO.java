package TommasoEleodori.Gestione_Eventi.exceptions;

import java.util.Date;
import java.util.List;

public record ErrorsResponseListDTO(String message, Date timestamp, List<String> errorsList) {
}
