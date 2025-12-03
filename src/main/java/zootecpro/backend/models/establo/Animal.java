package zootecpro.backend.models.establo;

import java.time.LocalDateTime;
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
import zootecpro.backend.models.enfermedad.Enfermedad;

@Entity
@Table(indexes = {
    @Index(columnList = "codigo", name = "CodigoAnimalIdx", unique = true),
    @Index(columnList = "identificadorElectronico", name = "IdentificadorElectronicoIdx", unique = true)
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
  @Id
  private UUID id;
  private String descripcion;
  private String codigo;
  private String identificadorElectronico;
  private String proposito;
  private String otroIdentificador;
  private String color;
  private LocalDateTime fechaNacimiento;
  private String observaciones;
  private Boolean genero;
  @ManyToOne(fetch = FetchType.EAGER)
  private TipoAnimal tipoAnimal;
  @OneToOne
  private Animal padre;
  @OneToOne
  private Animal madre;
  @OneToMany
  private List<Enfermedad> enfermedades;
  @OneToOne(fetch = FetchType.LAZY)
  private Establo establo;
}
