package zootecpro.backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import zootecpro.backend.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
  public Optional<Usuario> findByNombreUsuario(String nombreUsuario);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u JOIN u.rol r WHERE r.nombre = ?1")
  public Boolean existsUsuarioByRol(String rol);

  @Query("SELECT u from Usuario u JOIN u.rol r where r.nombre = ?1")
  public Optional<Usuario> findUsuarioByRol(String rol);
}
