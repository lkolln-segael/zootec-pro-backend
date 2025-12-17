package zootecpro.backend.models.dto.sanidad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnfermedadForm {
  private String nombre;
  private String descripcion;
  private String idTipoEnfermedad;
}
