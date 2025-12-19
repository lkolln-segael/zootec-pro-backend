package zootecpro.backend.models.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data // Incluye getters, setters, toString, equals, hashCode
public class InsertTrabajador {
  private String nombre;
  private String nombreUsuario;

  @JsonProperty("contraseña")
  private String contraseña;

  private String idRol;
}
