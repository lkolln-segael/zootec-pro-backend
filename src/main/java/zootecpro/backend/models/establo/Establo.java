package zootecpro.backend.models.establo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;
import zootecpro.backend.models.Usuario;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
    @Index(columnList = "nombre", name = "NombreEstabloIdx", unique = true)
})
public class Establo {
  @Id
  private UUID id;
  private String nombre;
  private SistemaProduccion sistemaProduccion;
  private Integer capacidadMaxima;
  private Integer areaTotal;
  private Integer areaPasto;
  private Integer areaBosque;
  private Integer areaCultivos;
  private Integer areaConstruida;
  @Default
  private LocalDate fechaCreacion = LocalDate.now();
  private LocalDate fechaModificacion;
  private String ubicacion;
  @ManyToOne
  private Usuario usuario;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Usuario> trabajadores;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "establo")
  List<Animal> animales;
}
