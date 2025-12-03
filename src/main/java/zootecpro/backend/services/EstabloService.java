package zootecpro.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.Usuario;
import zootecpro.backend.models.dto.EstabloForm;
import zootecpro.backend.models.establo.Establo;
import zootecpro.backend.repositories.EstabloRepository;
import zootecpro.backend.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstabloService {

  private final EstabloRepository establoRepository;

  private final UsuarioRepository usuarioRepository;

  public boolean insertEstablo(EstabloForm establoForm) {
    // Implementation goes here
    Optional<Establo> establoOpt = establoRepository.findByNombre(establoForm.nombre);
    if (establoOpt.isPresent()) {
      return false;
    }
    Optional<Usuario> usuarioOpt = usuarioRepository.findById(UUID.fromString(establoForm.idUsuario));
    if (usuarioOpt.isEmpty()) {
      log.info("Usuario no encontrado");
      return false;
    }
    Usuario usuario = usuarioOpt.get();
    establoRepository.save(Establo.builder()
        .id(UUID.randomUUID())
        .nombre(establoForm.nombre)
        .areaBosque(establoForm.areaBosque)
        .areaConstruida(establoForm.areaConstruida)
        .areaCultivos(establoForm.areaCultivos)
        .areaPasto(establoForm.areaPasto)
        .areaTotal(establoForm.areaTotal)
        .capacidadMaxima(establoForm.capacidadMaxima)
        .ubicacion(establoForm.ubicacion)
        .usuario(usuario)
        .build());
    return true;
  }

  public List<Establo> getEstablos() {
    return establoRepository.findAll();
  }

  public boolean updateEstablo(String id, EstabloForm establoForm) {
    Optional<Establo> establoOpt = establoRepository.findByNombre(establoForm.nombre);
    if (establoOpt.isEmpty()) {
      return false;
    }
    Optional<Usuario> usuarioOpt = usuarioRepository.findById(UUID.fromString(establoForm.idUsuario));
    if (usuarioOpt.isEmpty()) {
      log.info("Usuario no encontrado");
      return false;
    }
    Usuario usuario = usuarioOpt.get();
    establoRepository.save(Establo.builder()
        .id(establoOpt.get().getId())
        .nombre(establoForm.nombre)
        .areaBosque(establoForm.areaBosque)
        .areaConstruida(establoForm.areaConstruida)
        .areaCultivos(establoForm.areaCultivos)
        .areaPasto(establoForm.areaPasto)
        .areaTotal(establoForm.areaTotal)
        .capacidadMaxima(establoForm.capacidadMaxima)
        .ubicacion(establoForm.ubicacion)
        .usuario(usuario)
        .build());
    return true;
  }

  public boolean deleteEstablo(String id) {
    Optional<Establo> establoOpt = establoRepository.findById(UUID.fromString(id));
    if (establoOpt.isEmpty()) {
      return false;
    }
    establoRepository.delete(establoOpt.get());
    return true;
  }

  public Optional<Establo> getEstabloById(String id) {
    return establoRepository.findById(UUID.fromString(id));
  }
}
