package zootecpro.backend.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.Usuario;
import zootecpro.backend.models.dto.establo.EstabloForm;
import zootecpro.backend.models.establo.Establo;
import zootecpro.backend.repositories.EstabloRepository;
import zootecpro.backend.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstabloService {

  private final EstabloRepository establoRepository;

  private final UsuarioRepository usuarioRepository;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

  public boolean insertTrabajador(UUID establoId, Usuario usuario) {
    var establoOpt = establoRepository.findById(establoId);
    IO.println(usuario);
    if (establoOpt.isEmpty()) {
      return false;
    }
    var establo = establoOpt.get();
    if (establo.getTrabajadores() == null) {
      establo.setTrabajadores(new ArrayList<>());
    }
    usuario.setContraseña(encoder.encode(usuario.getContraseña()));
    usuario.setActivo(true);
    usuario.setFechaCreacion(LocalDate.now());
    usuario.setFechaModificacion(LocalDate.now());
    establo.getTrabajadores().add(usuario);
    this.establoRepository.save(establo);
    return true;
  }

  public String updateEstablo(String id, EstabloForm establoForm) {
    Optional<Establo> establoOpt = establoRepository.findById(UUID.fromString(id));
    if (establoOpt.isEmpty()) {
      return "Establo no encontrado";
    }
    Establo establo = establoOpt.get();
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
        .usuario(establo.getUsuario())
        .build());
    return "Establo actualizado con exito";
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
