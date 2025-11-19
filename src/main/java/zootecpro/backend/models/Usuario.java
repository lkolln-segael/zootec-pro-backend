package zootecpro.backend.models;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(indexes = {
    @Index(columnList = "nombreUsuario", name = "UsuarioNombreUsuarioIdx", unique = true)
})
public class Usuario {
  @Id
  private UUID id;
  private String nombreUsuario;
  private String contraseña;
  @OneToOne(fetch = FetchType.EAGER)
  private Rol rol;
}
