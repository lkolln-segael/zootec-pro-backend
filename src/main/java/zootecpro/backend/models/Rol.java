package zootecpro.backend.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Rol {
  @Id
  private UUID id;
  private String nombre;
  private List<String> permisos;
}
