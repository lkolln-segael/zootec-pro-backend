package zootecpro.backend.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zootecpro.backend.models.dto.reproduccion.AbortoForm;
import zootecpro.backend.models.dto.reproduccion.ConfirmacionPrenezForm;
import zootecpro.backend.models.dto.reproduccion.PartoForm;
import zootecpro.backend.models.dto.reproduccion.PrenezForm;
import zootecpro.backend.models.dto.reproduccion.SecaForm;
import zootecpro.backend.models.registros.Aborto;
import zootecpro.backend.models.registros.ConfirmacionPrenez;
import zootecpro.backend.models.registros.Parto;
import zootecpro.backend.models.registros.Prenez;
import zootecpro.backend.models.registros.RegistroReproduccion;
import zootecpro.backend.models.registros.Seca;
import zootecpro.backend.models.registros.TipoPrenez;
import zootecpro.backend.repositories.animal.AnimalRepository;
import zootecpro.backend.repositories.reproduccion.AbortoRepository;
import zootecpro.backend.repositories.reproduccion.ConfirmacionPrenezRepository;
import zootecpro.backend.repositories.reproduccion.PartoRepository;
import zootecpro.backend.repositories.reproduccion.PrenezRepository;
import zootecpro.backend.repositories.reproduccion.RegistroReproduccionRepository;
import zootecpro.backend.repositories.reproduccion.SecaRepository;

@Service
@RequiredArgsConstructor
public class ReproduccionService {

  private final AbortoRepository abortoRepository;

  private final ConfirmacionPrenezRepository confirmacionPrenezRepository;

  private final PartoRepository partoRepository;
  private final PrenezRepository prenezRepository;
  private final RegistroReproduccionRepository registroRepository;
  private final SecaRepository secaRepository;

  private final AnimalRepository animalRepository;

  private RegistroReproduccion generateRegistro() {
    return RegistroReproduccion.builder()
        .id(UUID.randomUUID())
        .fechaRegistro(LocalDate.now())
        .build();
  }

  public boolean insertarRegistroReproduccionSeca(SecaForm secaForm, String animalId) {
    var animalOpt = this.animalRepository.findById(UUID.fromString(animalId));
    if (animalOpt.isEmpty()) {
      return false;
    }
    var animal = animalOpt.get();
    var reproducciones = animal.getReproducciones();
    if (reproducciones == null) {
      reproducciones = new ArrayList<>();
    }
    var reproduccion = generateRegistro();
    var seca = Seca.builder()
        .id(UUID.randomUUID())
        .motivo(secaForm.motivo())
        .registroReproduccion(reproduccion)
        .build();
    reproducciones.add(
        reproduccion);
    animal.setReproducciones(reproducciones);
    animalRepository.save(animal);
    secaRepository.save(seca);
    return true;
  }

  public boolean insertarRegistroReproduccionPrenez(PrenezForm prenezForm, String animalId) {
    var animalOpt = this.animalRepository.findById(UUID.fromString(animalId));
    if (animalOpt.isEmpty()) {
      return false;
    }
    var animal = animalOpt.get();
    var reproducciones = animal.getReproducciones();
    if (reproducciones == null) {
      reproducciones = new ArrayList<>();
    }
    var reproduccion = generateRegistro();

    var madreOpt = animalRepository.findById(UUID.fromString(prenezForm.madreId()));
    var padreOpt = animalRepository.findById(UUID.fromString(prenezForm.padreId()));
    if (madreOpt.isEmpty() || padreOpt.isEmpty()) {
      return false;
    }

    var prenez = Prenez.builder()
        .id(UUID.randomUUID())
        .fechaCelo(prenezForm.fechaCelo())
        .fechaInseminacion(prenezForm.fechaInseminacion())
        .fechaDiagnostico(prenezForm.fechaDiagnostico())
        .registroReproduccion(reproduccion)
        .madre(madreOpt.get())
        .padre(padreOpt.get())
        .build();

    reproducciones.add(reproduccion);
    animal.setReproducciones(reproducciones);
    animalRepository.save(animal);

    prenezRepository.save(prenez);
    return true;
  }

  public boolean insertarRegistroReproduccionConfirmacionPrenez(ConfirmacionPrenezForm confirmacionForm,
      String animalId) {
    var animalOpt = this.animalRepository.findById(UUID.fromString(animalId));
    if (animalOpt.isEmpty()) {
      return false;
    }
    var animal = animalOpt.get();
    var reproducciones = animal.getReproducciones();
    if (reproducciones == null) {
      reproducciones = new ArrayList<>();
    }
    var reproduccion = generateRegistro();
    Map<String, TipoPrenez> tipoMap = Map.of(
        "TACTO", TipoPrenez.TACTO,
        "ECOGRAFIA", TipoPrenez.ECOGRAFIA,
        "VISUAL", TipoPrenez.VISUAL);
    String tipo = "";
    if (!tipoMap.containsKey(confirmacionForm.tipo())) {
      tipo = "TACTO";
    } else {
      tipo = confirmacionForm.tipo();
    }
    var confirmacionPrenez = ConfirmacionPrenez.builder()
        .id(UUID.randomUUID())
        .tipo(tipoMap.get(tipo))
        .registroReproduccion(reproduccion)
        .build();
    reproducciones.add(reproduccion);
    animal.setReproducciones(reproducciones);
    animalRepository.save(animal);

    this.confirmacionPrenezRepository.save(confirmacionPrenez);
    return true;
  }

  public boolean insertarRegistroReproduccionParto(PartoForm partoForm, String animalId) {
    var animalOpt = this.animalRepository.findById(UUID.fromString(animalId));
    if (animalOpt.isEmpty()) {
      return false;
    }
    var animal = animalOpt.get();
    var reproducciones = animal.getReproducciones();
    if (reproducciones == null) {
      reproducciones = new ArrayList<>();
    }
    var reproduccion = generateRegistro();
    var parto = Parto.builder()
        .id(UUID.randomUUID())
        .numero(partoForm.numero())
        .registroReproduccion(reproduccion)
        .build();

    reproducciones.add(reproduccion);
    animal.setReproducciones(reproducciones);
    animalRepository.save(animal);

    this.partoRepository.save(parto);
    return true;
  }

  public boolean insertarRegistroReproduccionAborto(AbortoForm abortoForm, String animalId) {
    var animalOpt = this.animalRepository.findById(UUID.fromString(animalId));
    if (animalOpt.isEmpty()) {
      return false;
    }
    var animal = animalOpt.get();
    var reproducciones = animal.getReproducciones();
    if (reproducciones == null) {
      reproducciones = new ArrayList<>();
    }
    var reproduccion = generateRegistro();
    var aborto = Aborto.builder()
        .id(UUID.randomUUID())
        .tipo(abortoForm.tipo())
        .build();

    reproducciones.add(reproduccion);
    animal.setReproducciones(reproducciones);
    animalRepository.save(animal);

    abortoRepository.save(aborto);
    return true;
  }

  public boolean eliminarRegistroReproduccion() {
    return false;
  }
}
