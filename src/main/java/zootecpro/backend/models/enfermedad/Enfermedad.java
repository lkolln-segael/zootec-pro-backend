package zootecpro.backend.models.enfermedad;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zootecpro.backend.models.establo.Animal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
    @Index(columnList = "nombre", name = "NombreEnfermedadIdx", unique = true)
})
@Entity
public class Enfermedad {
  @Id
  public UUID id;
  private String nombre;
  @OneToOne
  private TipoEnfermedad tipoEnfermedad;
  @OneToMany
  private List<Tratamiento> tratamientos;
  @ManyToOne(fetch = FetchType.LAZY)
  private Animal animal;
}
