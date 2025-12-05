package zootecpro.backend.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import zootecpro.backend.models.crecimiento.DesarrolloCrecimiento;
import zootecpro.backend.models.dto.AnimalExtended;
import zootecpro.backend.models.dto.AnimalForm;
import zootecpro.backend.models.dto.TipoAnimalForm;
import zootecpro.backend.models.establo.Animal;
import zootecpro.backend.models.establo.Establo;
import zootecpro.backend.models.establo.TipoAnimal;
import zootecpro.backend.repositories.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalService {

  private final AnimalRepository repository;
  private final TipoAnimalRepository tipoAnimalRepository;
  private final EstabloRepository establoRepository;

  public boolean insertAnimal(String establoId, AnimalForm animal) {
    Optional<Establo> establoOpt = this.establoRepository.findById(UUID.fromString(establoId));
    if (establoOpt.isEmpty()) {
      return false;
    }
    Establo establo = establoOpt.get();

    Optional<TipoAnimal> tipoAnimalOpt = this.tipoAnimalRepository.findById(UUID.fromString(animal.idTipoAnimal));
    if (tipoAnimalOpt.isEmpty()) {
      return false;
    }

    TipoAnimal tipoAnimal = tipoAnimalOpt.get();
    Animal animalModel = Animal.builder()
        .id(UUID.randomUUID())
        .codigo(animal.codigo)
        .descripcion(animal.descripcion)
        .identificadorElectronico(animal.identificadorElectronico)
        .proposito(animal.proposito)
        .color(animal.color)
        .fechaNacimiento(animal.fechaNacimiento)
        .observaciones(animal.observaciones)
        .genero(animal.genero)
        .establo(establo)
        .tipoAnimal(tipoAnimal)
        .build();

    if (animal.idMadre != null) {
      Optional<Animal> madreOpt = repository.findById(UUID.fromString(animal.idMadre.get()));
      if (madreOpt.isPresent()) {
        animalModel.setMadre(madreOpt.get());
      }
    }
    if (animal.idPadre != null) {
      Optional<Animal> padreOpt = repository.findById(UUID.fromString(animal.idPadre.get()));
      if (padreOpt.isPresent()) {
        animalModel.setPadre(padreOpt.get());
      }
    }
    repository.save(animalModel);
    return true;
  }

  public Optional<Animal> getAnimalById(String id) {
    return repository.findById(UUID.fromString(id));
  }

  public boolean updateAnimal(String establoId, String id, AnimalForm animal) {
    Optional<Establo> establoOpt = this.establoRepository.findById(UUID.fromString(establoId));
    if (establoOpt.isEmpty()) {
      return false;
    }
    Establo establo = establoOpt.get();

    Optional<TipoAnimal> tipoAnimalOpt = this.tipoAnimalRepository.findById(UUID.fromString(animal.idTipoAnimal));
    if (tipoAnimalOpt.isEmpty()) {
      return false;
    }

    TipoAnimal tipoAnimal = tipoAnimalOpt.get();
    Optional<Animal> animalOpt = repository.findById(UUID.fromString(id));
    if (animalOpt.isEmpty()) {
      return false;
    }
    Animal animalModel = animalOpt.get();

    animalModel.setDescripcion(animal.descripcion);
    animalModel.setCodigo(animal.codigo);
    animalModel.setIdentificadorElectronico(animal.identificadorElectronico);
    animalModel.setProposito(animal.proposito);
    animalModel.setColor(animal.color);
    animalModel.setFechaNacimiento(animal.fechaNacimiento);
    animalModel.setObservaciones(animal.observaciones);
    animalModel.setGenero(animal.genero);
    animalModel.setEstablo(establo);
    animalModel.setTipoAnimal(tipoAnimal);

    if (animal.idMadre != null) {
      Optional<Animal> madreOpt = repository.findById(UUID.fromString(animal.idMadre.get()));
      if (madreOpt.isPresent()) {
        animalModel.setMadre(madreOpt.get());
      }
    }
    if (animal.idPadre != null) {
      Optional<Animal> padreOpt = repository.findById(UUID.fromString(animal.idPadre.get()));
      if (padreOpt.isPresent()) {
        animalModel.setPadre(padreOpt.get());
      }
    }
    repository.save(animalModel);
    return true;
  }

  public boolean insertTipoAnimal(TipoAnimalForm tipoAnimal) {
    if (this.tipoAnimalRepository.existsByNombre(tipoAnimal.getNombre())) {
      return false;
    }
    this.tipoAnimalRepository.save(TipoAnimal.builder()
        .id(UUID.randomUUID())
        .nombre(tipoAnimal.getNombre())
        .codigo(tipoAnimal.getCodigo())
        .build());
    return true;
  }

  public List<TipoAnimal> getAllTiposAnimal() {
    return this.tipoAnimalRepository.findAll();
  }

  public boolean updateTipoAnimal(String id, TipoAnimalForm tipoAnimal) {
    var tipoOpt = this.tipoAnimalRepository.findById(UUID.fromString(id));
    if (tipoOpt.isEmpty()) {
      return false;
    }
    var tipo = tipoOpt.get();
    tipo.setNombre(tipoAnimal.getNombre());
    tipo.setCodigo(tipoAnimal.getCodigo());
    this.tipoAnimalRepository.save(tipo);
    return true;
  }

  public boolean deleteTipoAnimal(String id) {
    var tipoOpt = this.tipoAnimalRepository.findById(UUID.fromString(id));
    if (tipoOpt.isEmpty()) {
      return false;
    }
    this.tipoAnimalRepository.delete(tipoOpt.get());
    return true;
  }

  public List<Animal> getAllAnimals(String establoId) {
    return this.repository.findAll()
        .stream()
        .filter(animal -> animal.getEstablo().getId().toString().equals(establoId))
        .map(animal -> {
          animal.setEstablo(null);
          return animal;
        }).toList();
  }

  public boolean insertAnimalExtended(String establoId, AnimalExtended animal) {
    // Implementation goes here
    //
    var establoOpt = this.establoRepository.findById(UUID.fromString(establoId));
    if (!establoOpt.isPresent()) {
      return false;
    }
    var tipoAnimalOpt = this.tipoAnimalRepository.findById(UUID.fromString(animal.idTipoAnimal));
    if (!tipoAnimalOpt.isPresent()) {
      return false;
    }
    var establo = establoOpt.get();
    var tipoAnimal = tipoAnimalOpt.get();

    var animalModel = Animal.builder()
        .id(UUID.randomUUID())
        .tipoAnimal(tipoAnimal)
        .codigo(animal.codigoAsosiacion)
        .descripcion(animal.descripcion)
        .identificadorElectronico(animal.identificadorElectronico)
        .proposito(animal.proposito)
        .color(animal.color)
        .genero(animal.genero)
        .establo(establo)
        .fechaNacimiento(animal.fechaNacimiento)
        .observaciones(animal.observacionNacimiento + " " + animal.observacionParto)
        .desarrollosCrecimiento(List.of(
            DesarrolloCrecimiento.builder()
                .id(UUID.randomUUID())
                .estado("NACIMIENTO")
                .fechaRegistro(LocalDateTime.now())
                .pesoActual(animal.pesoActual)
                .tamaño(animal.tamanoActual.doubleValue())
                .condicionCorporal(animal.condicionCorporal)
                .unidadesAnimal(animal.unidadesAnimales)
                .build()))
        .build();

    this.repository.save(animalModel);
    return true;
  }

  public boolean deleteAnimal(String id) {
    var animalOpt = this.repository.findById(UUID.fromString(id));
    if (animalOpt.isEmpty()) {
      return false;
    }
    this.repository.delete(animalOpt.get());
    return true;
  }
}
