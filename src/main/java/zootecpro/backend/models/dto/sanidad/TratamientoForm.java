package zootecpro.backend.models.dto.sanidad;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TratamientoForm {
  public String nombre;
  public String descripcion;
}
