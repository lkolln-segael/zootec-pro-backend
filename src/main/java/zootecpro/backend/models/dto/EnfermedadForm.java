package zootecpro.backend.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnfermedadForm {
  private String nombre;
  private String descripcion;
}
