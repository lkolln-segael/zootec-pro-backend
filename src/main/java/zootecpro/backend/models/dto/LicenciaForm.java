package zootecpro.backend.models.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicenciaForm {
  public String nombre;
  public Integer limiteAnimales;
  public Double costo;
  public LocalDate fechaInicio;
  public LocalDate fechaFinal;
}
