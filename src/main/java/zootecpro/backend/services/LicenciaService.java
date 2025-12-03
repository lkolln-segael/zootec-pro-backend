package zootecpro.backend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.Licencia;
import zootecpro.backend.models.dto.LicenciaForm;
import zootecpro.backend.repositories.LicenciaRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LicenciaService {
  private final LicenciaRepository licenciaRepository;

  public List<Licencia> getAllLicencias() {
    log.info("Obteniendo todas las licencias");
    return licenciaRepository.findAll();
  }

  public boolean insertLicencia(LicenciaForm licenciaForm) {
    try {
      Licencia licencia = Licencia.builder()
          .id(UUID.randomUUID())
          .nombre(licenciaForm.nombre)
          .limiteAnimales(licenciaForm.limiteAnimales)
          .costo(licenciaForm.costo)
          .fechaInicio(licenciaForm.fechaInicio)
          .fechaFinal(licenciaForm.fechaFinal)
          .build();
      licenciaRepository.save(licencia);
      log.info("Licencia insertada correctamente");
      return true;
    } catch (Exception e) {
      log.error("Error al insertar la licencia: " + e.getMessage());
      return false;
    }
  }

  public Licencia getLicenciaById(UUID id) {
    Optional<Licencia> licenciaOpt = licenciaRepository.findById(id);
    if (licenciaOpt.isPresent()) {
      log.info("Licencia encontrada: " + id.toString());
      return licenciaOpt.get();
    } else {
      log.error("Licencia no encontrada: " + id.toString());
      return null;
    }
  }

  public boolean updateLicencia(LicenciaForm licencia) {
    try {
      Optional<Licencia> licenciaOpt = licenciaRepository.findByNombre(licencia.nombre);
      if (licenciaOpt.isEmpty()) {
        log.error("Licencia no encontrada: " + licencia.nombre);
        return false;
      }
      Licencia licenciaModel = licenciaOpt.get();
      licenciaModel.setNombre(licencia.nombre);
      licenciaModel.setLimiteAnimales(licencia.limiteAnimales);
      licenciaModel.setCosto(licencia.costo);
      licenciaModel.setFechaInicio(licencia.fechaInicio);
      licenciaModel.setFechaFinal(licencia.fechaFinal);
      licenciaModel.setFechaModificacion(LocalDate.now());
      licenciaRepository.save(licenciaModel);
      log.info("Licencia actualizada correctamente");
      return true;
    } catch (Exception e) {
      log.error("Error al actualizar la licencia: " + e.getMessage());
      return false;
    }
  }

  public boolean deleteLicencia(UUID id) {
    try {
      Optional<Licencia> licenciaOpt = licenciaRepository.findById(id);
      if (licenciaOpt.isEmpty()) {
        log.error("Licencia no encontrada: " + id.toString());
        return false;
      }
      licenciaRepository.delete(licenciaOpt.get());
      log.info("Licencia eliminada correctamente");
      return true;
    } catch (Exception e) {
      log.error("Error al eliminar la licencia: " + e.getMessage());
      return false;
    }
  }
}
