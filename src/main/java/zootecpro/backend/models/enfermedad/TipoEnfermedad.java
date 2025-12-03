package zootecpro.backend.models.enfermedad;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zootecpro.backend.models.establo.TipoAnimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TipoEnfermedad {
  @Id
  private UUID id;
  private String nombre;
  @OneToMany(cascade = CascadeType.ALL)
  private List<Sintoma> sintomas;
  @OneToMany(cascade = CascadeType.ALL)
  private List<TipoTratamiento> tratamientos;
  @OneToOne
  private TipoAnimal tipoAnimales;
}
