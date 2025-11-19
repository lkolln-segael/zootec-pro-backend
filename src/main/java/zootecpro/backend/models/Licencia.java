package zootecpro.backend.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Licencia {
  @Id
  private UUID id;
  private String nombre;
  private Integer limiteAnimales;
  private Double costo;
  private LocalDate fechaInicio;
  private LocalDate fechaFinal;
}
