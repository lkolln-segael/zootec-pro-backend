package zootecpro.backend.models.dto;

import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import zootecpro.backend.models.Licencia;
import zootecpro.backend.models.Rol;

@Data
@Builder
public class UsuarioSimplified {
  public UUID id;
  public String nombre;
  public String nombreUsuario;
  public Rol rol;
  public Optional<Licencia> licencia;
}
