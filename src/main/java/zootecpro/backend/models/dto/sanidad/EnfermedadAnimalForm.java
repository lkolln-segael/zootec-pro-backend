package zootecpro.backend.models.dto.sanidad;

import java.time.LocalDateTime;
import java.util.List;

public record EnfermedadAnimalForm(
    String animalId,
    String tipoEnfermedadId,
    List<String> sintomasId,
    List<String> tratamientosId,
    LocalDateTime fechaRegistro) {
};
