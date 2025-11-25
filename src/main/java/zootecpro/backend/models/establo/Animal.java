package zootecpro.backend.models.establo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import zootecpro.backend.models.enfermedad.Enfermedad;

@Entity
@Table(indexes = {

})
public class Animal {
  private UUID id;
  private String descripcion;
  private String codigo;
  private String identificadorElectronico;
  private String proposito;
  private String otroIdentificador;
  private String color;
  private LocalDateTime fechaNacimiento;
  @OneToOne
  private Animal padre;
  @OneToOne
  private Animal madre;
  @OneToMany
  private List<Enfermedad> enfermedades;
}
