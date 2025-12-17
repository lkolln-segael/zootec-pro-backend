package zootecpro.backend.models.dto.animal;

import java.time.LocalDateTime;

public record ProduccionForm(
    String animalId,
    Double pesoLeche,
    Double phLeche,
    Double ureaLeche,
    LocalDateTime fechaRegistro) {
}
