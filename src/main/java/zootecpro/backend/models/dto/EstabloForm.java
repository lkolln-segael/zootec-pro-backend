package zootecpro.backend.models.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstabloForm {
  public String nombre;
  public String sistemaProduccion;
  public Integer capacidadMaxima;
  public Integer areaTotal;
  public Integer areaPasto;
  public Integer areaBosque;
  public Integer areaCultivos;
  public Integer areaConstruida;
  public String ubicacion;
  public String idUsuario;
}
