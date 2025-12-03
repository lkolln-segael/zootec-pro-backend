package zootecpro.backend.models.dto;

import java.time.LocalDate;
import java.util.Optional;

import lombok.Builder;

import lombok.Data;

@Builder
@Data
public class InsertUsuario {
  public String nombre;
  public String nombreUsuario;
  public String contraseña;
  public String correo;
  public Optional<Roles> rol;
  @Builder.Default
  public LocalDate fechaCreacion = LocalDate.now();
  @Builder.Default
  public Boolean activo = true;
  @Builder.Default
  public LocalDate fechaModificacion = LocalDate.now();
}
