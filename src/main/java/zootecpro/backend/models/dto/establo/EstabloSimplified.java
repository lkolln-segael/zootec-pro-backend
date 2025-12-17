package zootecpro.backend.models.dto.establo;

import lombok.Builder;
import lombok.Data;
import zootecpro.backend.models.establo.SistemaProduccion;

@Data
@Builder
public class EstabloSimplified {
  public String id;
  public String nombre;
  public Integer capacidadMaxima;
  public SistemaProduccion sistemaProduccion;
  public String ubicacion;
}
