package zootecpro.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import zootecpro.backend.models.Usuario;  

public interface UsuarioRepository extends JpaRepository<Usuario,UUID>{
}
