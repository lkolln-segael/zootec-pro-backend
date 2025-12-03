package zootecpro.backend.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = { @Index(name = "NombreRolIdx", columnList = "nombre", unique = true) })
public class Rol {
  @Id
  private UUID id;
  private String nombre;
  private List<String> permisos;
  @Column(columnDefinition = "BOOL DEFAULT TRUE")
  @Builder.Default
  private Boolean enable = true;
}
