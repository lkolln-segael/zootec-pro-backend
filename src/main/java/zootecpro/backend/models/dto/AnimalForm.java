package zootecpro.backend.models.dto;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalForm {
  public String descripcion;
  public String codigo;
  public String identificadorElectronico;
  public String proposito;
  public String otroIdentificador;
  public String color;
  public String genero;
  public LocalDateTime fechaNacimiento;
  public String observaciones;
  public String idTipoAnimal;
  public Optional<String> idPadre;
  public Optional<String> idMadre;
  public String idEstablo;
}
