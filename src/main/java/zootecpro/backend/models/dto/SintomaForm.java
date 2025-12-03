package zootecpro.backend.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SintomaForm {
  public String nombre;
  public String descripcion;
}
