package zootecpro.backend.models.crecimiento;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TipoAlimento {
  @Id
  private UUID id;
  private String estado;
}
