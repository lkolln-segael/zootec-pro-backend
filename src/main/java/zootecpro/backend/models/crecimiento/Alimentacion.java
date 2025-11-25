package zootecpro.backend.models.crecimiento;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Alimentacion {
  @Id
  public UUID id;
  private String estado;
  @OneToOne
  private TipoAlimento tipoAlimento;
}
