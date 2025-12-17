package zootecpro.backend.models.dto.reproduccion;

import java.time.LocalDate;

public record PrenezForm(
    LocalDate fechaCelo,
    LocalDate fechaInseminacion,
    LocalDate fechaDiagnostico,
    String padreId,
    String madreId) {
}
