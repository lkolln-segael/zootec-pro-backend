package zootecpro.backend.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zootecpro.backend.models.dto.sanidad.EnfermedadAnimalForm;
import zootecpro.backend.models.dto.sanidad.EnfermedadForm;
import zootecpro.backend.models.dto.sanidad.SintomaForm;
import zootecpro.backend.models.dto.sanidad.TipoEnfermedadForm;
import zootecpro.backend.models.dto.sanidad.TratamientoForm;
import zootecpro.backend.models.enfermedad.Enfermedad;
import zootecpro.backend.models.enfermedad.Sintoma;
import zootecpro.backend.models.enfermedad.TipoEnfermedad;
import zootecpro.backend.models.enfermedad.TipoTratamiento;
import zootecpro.backend.models.enfermedad.Tratamiento;
import zootecpro.backend.models.establo.Animal;
import zootecpro.backend.models.establo.TipoAnimal;
import zootecpro.backend.repositories.animal.AnimalRepository;
import zootecpro.backend.repositories.animal.TipoAnimalRepository;
import zootecpro.backend.repositories.sanidad.EnfermedadRepository;
import zootecpro.backend.repositories.sanidad.TipoEnfermedadRepository;
import zootecpro.backend.repositories.sanidad.TipoTratamientoRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnfermedadService {

  private final TipoEnfermedadRepository tipoEnfermedadRepository;

  private final TipoTratamientoRepository tipoTratamientoRepository;

  private final TipoAnimalRepository tipoAnimalRepository;

  private final AnimalRepository animalRepository;

  private final EnfermedadRepository enfermedadRepository;

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

  public List<Enfermedad> getEnfermedades() {
    return this.enfermedadRepository.findAllEnfermedadesConAnimal()
        .stream()
        .map(e -> {
          var animal = new Animal();
          animal.setCodigo(e.getAnimal().getCodigo());
          e.setAnimal(animal);
          return e;
        })
        .toList();
  }

  public boolean insertEnfermedadCompleto(EnfermedadAnimalForm enfermedad) {
    log.info(enfermedad.toString());
    var animalOpt = this.animalRepository.findById(UUID.fromString(enfermedad.animalId()));
    if (animalOpt.isEmpty()) {
      return false;
    }
    Animal animal = animalOpt.get();
    var tipoEnfermedadOpt = this.tipoEnfermedadRepository.findById(UUID.fromString(enfermedad.tipoEnfermedadId()));
    if (tipoEnfermedadOpt.isEmpty()) {
      return false;
    }
    List<TipoTratamiento> tipoTratamiento = this.tipoTratamientoRepository.findAll();
    TipoEnfermedad tipoEnfermedad = tipoEnfermedadOpt.get();
    List<Enfermedad> enfermedades = animal.getEnfermedades();
    enfermedades.add(Enfermedad.builder()
        .id(UUID.randomUUID())
        .tratamientos(tipoTratamiento.stream()
            .map(t -> Tratamiento.builder()
                .id(UUID.randomUUID())
                .fechaRegistro(LocalDateTime.now())
                .nombre(t.getNombre())
                .tipoTratamiento(t)
                .build())
            .toList())
        .tipoEnfermedad(tipoEnfermedad)
        .animal(animal)
        .nombre(tipoEnfermedad.getNombre() + "-" + animal.getCodigo())
        .fechaRegistro(LocalDateTime.now())
        .build());
    this.animalRepository.save(animal);
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

  public boolean insertEnfermedad(String animalId, EnfermedadForm enfermedadForm) {
    var animalOpt = this.animalRepository.findById(UUID.fromString(animalId));
    if (animalOpt.isEmpty()) {
      return false;
    }

    var tipoEnfermedadOpt = this.tipoEnfermedadRepository
        .findById(UUID.fromString(enfermedadForm.getIdTipoEnfermedad()));
    if (tipoEnfermedadOpt.isEmpty()) {
      return false;
    }
    TipoEnfermedad tipoEnfermedad = tipoEnfermedadOpt.get();

    Animal animal = animalOpt.get();

    // Convertir EnfermedadForm a Entidad Enfermedad
    Enfermedad enfermedad = Enfermedad.builder()
        .nombre(enfermedadForm.getNombre())
        .tipoEnfermedad(tipoEnfermedad)
        .animal(animal) // Establecer la relación con el animal
        .build();

    // Inicializar la lista de enfermedades si es null
    if (animal.getEnfermedades() == null) {
      animal.setEnfermedades(new ArrayList<>());
    }

    // Agregar la enfermedad a la lista del animal
    animal.getEnfermedades().add(enfermedad);

    // Guardar el animal (la enfermedad se guardará por cascada)
    animalRepository.save(animal);

    return true;
  }

  public boolean editarEnfermedad(String enfermedadId, EnfermedadForm enfermedadForm) {
    var enfermedadOpt = this.enfermedadRepository.findById(UUID.fromString(enfermedadId));
    if (enfermedadOpt.isEmpty()) {
      return false;
    }

    var tipoEnfermedadOpt = this.tipoEnfermedadRepository
        .findById(UUID.fromString(enfermedadForm.getIdTipoEnfermedad()));
    if (tipoEnfermedadOpt.isEmpty()) {
      return false;
    }
    TipoEnfermedad tipoEnfermedad = tipoEnfermedadOpt.get();

    Enfermedad enfermedad = enfermedadOpt.get();

    enfermedad.setNombre(enfermedadForm.getNombre());
    enfermedad.setTipoEnfermedad(tipoEnfermedad);
    return true;
  }

  public boolean eliminarEnfermedad(String enfermedadId) {
    var enfermedadOpt = this.enfermedadRepository.findById(UUID.fromString(enfermedadId));
    if (enfermedadOpt.isEmpty()) {
      return false;
    }
    Enfermedad enfermedad = enfermedadOpt.get();
    enfermedadRepository.delete(enfermedad);
    return true;
  }

  public List<TipoTratamiento> getAllTiposTratamientos(String tipoId) {
    Optional<TipoEnfermedad> tipoOpt = this.tipoEnfermedadRepository.findById(UUID.fromString(tipoId));
    if (tipoOpt.isEmpty()) {
      return List.of();
    }
    return tipoOpt.get().getTratamientos();
  }
}
