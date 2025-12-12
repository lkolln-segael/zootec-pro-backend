package zootecpro.backend.models.enfermedad;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
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
    @Index(columnList = "nombre", name = "NombreEnfermedadIdx")
})
@Entity
public class Enfermedad {
  @Id
  public UUID id;
  private String nombre;
  @ManyToOne
  private TipoEnfermedad tipoEnfermedad;
  @OneToMany(cascade = CascadeType.ALL)
  private List<Tratamiento> tratamientos;
  @ManyToOne(fetch = FetchType.LAZY)
  private Animal animal;
  private LocalDateTime fechaRegistro;
}
