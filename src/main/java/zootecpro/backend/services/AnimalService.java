package zootecpro.backend.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import zootecpro.backend.models.crecimiento.DesarrolloCrecimiento;
import zootecpro.backend.models.dto.animal.AnimalExtended;
import zootecpro.backend.models.dto.animal.AnimalForm;
import zootecpro.backend.models.dto.animal.DesarrolloCrecimientoForm;
import zootecpro.backend.models.dto.animal.ProduccionForm;
import zootecpro.backend.models.dto.animal.TipoAnimalForm;
import zootecpro.backend.models.enfermedad.Enfermedad;
import zootecpro.backend.models.establo.Animal;
import zootecpro.backend.models.establo.Establo;
import zootecpro.backend.models.establo.TipoAnimal;
import zootecpro.backend.models.registros.RegistroProduccionLeche;
import zootecpro.backend.repositories.*;
import zootecpro.backend.repositories.animal.AnimalRepository;
import zootecpro.backend.repositories.animal.RegistroProduccionLecheRepository;
import zootecpro.backend.repositories.animal.TipoAnimalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalService {

  private final AnimalRepository repository;
  private final TipoAnimalRepository tipoAnimalRepository;
  private final EstabloRepository establoRepository;
  private final RegistroProduccionLecheRepository registroProduccionRepository;
  private final EntityManager entityManager;

  @Transactional
  public boolean insertAnimal(String establoId, AnimalForm animal) {
    Optional<Establo> establoOpt = this.establoRepository.findById(UUID.fromString(establoId));
    if (establoOpt.isEmpty()) {
      return false;
    }
    Establo establo = establoOpt.get();
    entityManager.detach(establo);
    Optional<TipoAnimal> tipoAnimalOpt = this.tipoAnimalRepository.findById(UUID.fromString(animal.idTipoAnimal));
    if (tipoAnimalOpt.isEmpty()) {
      return false;
    }

    TipoAnimal tipoAnimal = tipoAnimalOpt.get();
    entityManager.detach(tipoAnimal);
    Animal animalModel = Animal.builder()
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
      Animal madreReference = repository.getReferenceById(UUID.fromString(animal.idMadre.get()));
      animalModel.setMadre(madreReference);
    }

    if (animal.idPadre != null) {
      Animal padreReference = repository.getReferenceById(UUID.fromString(animal.idPadre.get()));
      animalModel.setPadre(padreReference);
    }
    this.repository.save(animalModel);
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

  @Transactional(readOnly = true)
  public List<Animal> getAllAnimals(String establoId) {
    var animales = this.repository.findAll();
    animales = animales.stream()
        .map(a -> {
          var enfermedades = a.getEnfermedades()
              .stream()
              .map(e -> {
                return Enfermedad.builder()
                    .id(e.getId())
                    .nombre(e.getNombre())
                    .fechaRegistro(e.getFechaRegistro())
                    .tipoEnfermedad(e.getTipoEnfermedad())
                    .build();
              })
              .toList();
          var produccion = a.getProduccionLeche();
          produccion.forEach(p -> p.setAnimal(null));
          return Animal.builder()
              .id(a.getId())
              .descripcion(a.getDescripcion())
              .codigo(a.getCodigo())
              .identificadorElectronico(a.getIdentificadorElectronico())
              .proposito(a.getProposito())
              .color(a.getColor())
              .fechaNacimiento(a.getFechaNacimiento())
              .observaciones(a.getObservaciones())
              .genero(a.getGenero())
              .tipoAnimal(a.getTipoAnimal())
              .padre(a.getPadre())
              .madre(a.getMadre())
              .enfermedades(enfermedades)
              .desarrollosCrecimiento(a.getDesarrollosCrecimiento())
              .produccionLeche(a.getProduccionLeche())
              .reproducciones(a.getReproducciones())
              .build();
        })
        .toList();
    return animales;
  }

  public boolean insertProduccion(ProduccionForm produccion) {
    var animalOpt = this.repository.findById(UUID.fromString(produccion.animalId()));
    if (!animalOpt.isPresent()) {
      return false;
    }
    Animal animal = animalOpt.get();
    if (animal.getProduccionLeche() == null) {
      animal.setProduccionLeche(new ArrayList<>());
    }
    animal.getProduccionLeche().add(RegistroProduccionLeche.builder()
        .id(UUID.randomUUID())
        .litrosLeche(produccion.pesoLeche())
        .ureaLeche(produccion.ureaLeche())
        .fechaRegistro(produccion.fechaRegistro())
        .phLeche(produccion.phLeche())
        .animal(animal)
        .build());
    this.repository.save(animal);
    return true;
  }

  public List<RegistroProduccionLeche> getAllProducciones() {
    return this.registroProduccionRepository.findAllRegistrosConAnimal().stream().map(m -> {
      var animal = new Animal();
      animal.setCodigo(m.getAnimal().getCodigo());
      animal.setEstablo(Establo.builder().id(m.getAnimal().getEstablo().getId()).build());
      m.setAnimal(animal);
      return m;
    }).toList();
  }

  public boolean insertAnimalExtended(String establoId, AnimalExtended animal) {
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

  public boolean insertCrecimiento(DesarrolloCrecimientoForm desarrollo) {
    var animalOpt = this.repository.findById(UUID.fromString(desarrollo.animalId()));
    if (animalOpt.isEmpty()) {
      return false;
    }
    var animal = animalOpt.get();
    var desarrolloCrecimientos = animal.getDesarrollosCrecimiento();
    if (desarrolloCrecimientos == null) {
      desarrolloCrecimientos = new ArrayList<>();
    }
    desarrolloCrecimientos.add(
        DesarrolloCrecimiento.builder()
            .id(UUID.randomUUID())
            .pesoActual(desarrollo.pesoActual())
            .tamaño(desarrollo.tamaño())
            .estado(desarrollo.estado())
            .condicionCorporal(desarrollo.condicionCorporal())
            .unidadesAnimal(desarrollo.unidadesAnimal())
            .build());
    animal.setDesarrollosCrecimiento(desarrolloCrecimientos);
    this.repository.save(animal);
    return true;
  }
}
