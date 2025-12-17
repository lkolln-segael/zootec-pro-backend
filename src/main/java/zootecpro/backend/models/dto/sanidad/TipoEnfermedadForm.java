package zootecpro.backend.models.dto.sanidad;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TipoEnfermedadForm {
  private String nombre;
  private String tipoAnimalId;
}
