package zootecpro.backend.models.establo;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TipoAnimal {
  @Id
  private UUID id;
  private String nombre;
  private String codigo;
}
