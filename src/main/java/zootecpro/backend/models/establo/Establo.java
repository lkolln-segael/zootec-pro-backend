package zootecpro.backend.models.establo;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
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
}
