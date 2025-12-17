package zootecpro.backend.models.dto.reproduccion;

import java.time.LocalDate;

public record ReproduccionForm(
    String animalId,
    String tipoConcepcion,
    LocalDate fechaCelo,
    LocalDate fechaInseminacion,
    LocalDate fechaDiagnostico) {
}
