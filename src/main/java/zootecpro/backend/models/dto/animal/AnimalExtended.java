package zootecpro.backend.models.dto.animal;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimalExtended {
  public String nombre;
  public String codigoAsosiacion;
  public String codigoIngreso;
  public String codigoSalida;
  public String condicionCorporal;
  public String descripcion;
  public String color;
  public Boolean genero;
  public LocalDateTime fechaNacimiento;
  public String idTipoAnimal;
  public String identificadorElectronico;
  public String observacionNacimiento;
  public String observacionParto;
  public Integer pesoActual;
  public Integer tamanoActual;
  public String proposito;
  public String tipoIngreso;
  public String tipoSalida;
  public String unidadesAnimales;
}
