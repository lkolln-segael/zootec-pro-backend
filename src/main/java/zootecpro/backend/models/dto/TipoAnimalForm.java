package zootecpro.backend.models.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TipoAnimalForm {
  public String nombre;
  public String codigo;
}
