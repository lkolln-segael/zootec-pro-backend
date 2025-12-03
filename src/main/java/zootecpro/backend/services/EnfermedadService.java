package zootecpro.backend.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.dto.EnfermedadForm;
import zootecpro.backend.models.dto.SintomaForm;
import zootecpro.backend.models.dto.TipoEnfermedadForm;
import zootecpro.backend.models.dto.TratamientoForm;
import zootecpro.backend.models.enfermedad.Sintoma;
import zootecpro.backend.models.enfermedad.TipoEnfermedad;
import zootecpro.backend.models.enfermedad.TipoTratamiento;
import zootecpro.backend.models.establo.TipoAnimal;
import zootecpro.backend.repositories.AnimalRepository;
import zootecpro.backend.repositories.TipoAnimalRepository;
import zootecpro.backend.repositories.TipoEnfermedadRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnfermedadService {

  private final TipoEnfermedadRepository tipoEnfermedadRepository;

  private final TipoAnimalRepository tipoAnimalRepository;

  private final AnimalRepository animalRepository;

  public boolean insertTipoEnfermedad(TipoEnfermedadForm tipoEnfermedad) {
    log.info("Insertando tipo de enfermedad: " + tipoEnfermedad.toString());
    if (this.tipoEnfermedadRepository.existsByNombre(tipoEnfermedad.getNombre())) {
      log.error("Encontro el tipo de enfermedad repetido");
      return false;
    }
    Optional<TipoAnimal> tipoAnimalOpt = this.tipoAnimalRepository
        .findById(UUID.fromString(tipoEnfermedad.getTipoAnimalId()));
    if (!tipoAnimalOpt.isPresent()) {
      log.error("No encontro el tipo de animal");
      return false;
    }
    log.info(tipoAnimalOpt.get().toString());
    this.tipoEnfermedadRepository.save(
        zootecpro.backend.models.enfermedad.TipoEnfermedad.builder()
            .id(UUID.randomUUID())
            .nombre(tipoEnfermedad.getNombre())
            .tipoAnimales(tipoAnimalOpt.get())
            .build());
    return true;
  }

  public List<TipoEnfermedad> getAllTiposEnfermedades() {
    return this.tipoEnfermedadRepository.findAll();
  }

  public boolean updateTipoEnfermedad(String id, TipoEnfermedadForm tipoEnfermedad) {
    var tipoOpt = this.tipoEnfermedadRepository.findById(UUID.fromString(id));
    if (tipoOpt.isEmpty()) {
      return false;
    }
    var tipoAnimalOpt = this.tipoAnimalRepository.findById(UUID.fromString(tipoEnfermedad.getTipoAnimalId()));
    if (tipoAnimalOpt.isEmpty()) {
      return false;
    }
    var tipo = tipoOpt.get();
    tipo.setNombre(tipoEnfermedad.getNombre());
    tipo.setTipoAnimales(tipoAnimalOpt.get());
    this.tipoEnfermedadRepository.save(tipo);
    return true;
  }

  public boolean deleteTipoEnfermedad(String id) {
    var tipoOpt = this.tipoEnfermedadRepository.findById(UUID.fromString(id));
    if (tipoOpt.isEmpty()) {
      return false;
    }
    this.tipoEnfermedadRepository.delete(tipoOpt.get());
    return true;
  }

  public boolean insertSintomas(String id, List<SintomaForm> sintomas) {
    var tipoOpt = this.tipoEnfermedadRepository.findById(UUID.fromString(id));
    if (tipoOpt.isEmpty()) {
      return false;
    }
    var tipo = tipoOpt.get();
    if (tipo.getSintomas() == null) {
      tipo.setSintomas(Collections.emptyList());
    }
    tipo.getSintomas().addAll(
        sintomas.stream()
            .map(s -> Sintoma.builder()
                .id(UUID.randomUUID())
                .nombre(s.getNombre())
                .descripcion(s.getDescripcion())
                .build())
            .toList());
    this.tipoEnfermedadRepository.save(tipo);
    return true;
  }

  public boolean insertTratamientos(String id, List<TratamientoForm> tratamientos) {
    var tipoOpt = this.tipoEnfermedadRepository.findById(UUID.fromString(id));
    if (tipoOpt.isEmpty()) {
      return false;
    }
    var tipo = tipoOpt.get();
    if (tipo.getTratamientos() == null) {
      tipo.setTratamientos(Arrays.asList());
    }
    tipo.getTratamientos().addAll(
        tratamientos.stream()
            .map(t -> TipoTratamiento.builder()
                .id(UUID.randomUUID())
                .nombre(t.getNombre())
                .descripcion(t.getDescripcion())
                .build())
            .toList());
    this.tipoEnfermedadRepository.save(tipo);
    return true;
  }

  public boolean insertEnfermedad(String animalId, EnfermedadForm enfermedad) {
    var animalOpt = this.
    return true;
  }
}
