package zootecpro.backend.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
    @Index(columnList = "nombreUsuario", name = "UsuarioNombreUsuarioIdx", unique = true)
})
public class Usuario implements UserDetails {
  @Id
  private UUID id;
  private String nombre;
  private String nombreUsuario;
  private String contraseña;
  @OneToOne(fetch = FetchType.EAGER)
  private Rol rol;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<String> permisos = rol.getPermisos();
    List<GrantedAuthority> authorities = new ArrayList<>();
    permisos.forEach(p -> {
      authorities.add(new SimpleGrantedAuthority(p));
    });
    return authorities;
  }

  @Override
  public String getPassword() {
    return contraseña;
  }

  @Override
  public String getUsername() {
    return nombreUsuario;
  }
}
