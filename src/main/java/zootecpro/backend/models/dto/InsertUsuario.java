package zootecpro.backend.models.dto;

import java.util.Optional;

import lombok.Builder;

import lombok.Data;

@Builder
@Data
public class InsertUsuario {
  public String nombre;
  public String nombreUsuario;
  public String contraseña;
  public Optional<Roles> rol;
}
