package zootecpro.backend.models.enfermedad;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class TipoEnfermedad {
  @Id
  private UUID id;
  private String nombre;
  @OneToMany
  private List<Sintoma> sintomas;
  @OneToMany
  private List<Tratamiento> tratamientos;
}
