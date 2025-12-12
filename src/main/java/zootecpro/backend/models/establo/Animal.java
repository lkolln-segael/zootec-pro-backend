package zootecpro.backend.models.establo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zootecpro.backend.models.crecimiento.DesarrolloCrecimiento;
import zootecpro.backend.models.enfermedad.Enfermedad;
import zootecpro.backend.models.registros.RegistroProduccion;

@Entity
@Table(indexes = {
    @Index(columnList = "codigo", name = "CodigoAnimalIdx", unique = true),
    @Index(columnList = "identificadorElectronico", name = "IdentificadorElectronicoIdx", unique = true)
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal implements Cloneable {
  @Id
  @GeneratedValue
  private UUID id;

  private String descripcion;
  private String codigo;

  @Column(unique = true)
  private String identificadorElectronico;

  private String proposito;
  private String color;

  @Column(name = "fecha_nacimiento")
  private LocalDateTime fechaNacimiento;

  private String observaciones;

  private Boolean genero;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "tipo_animal_id")
  private TipoAnimal tipoAnimal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "padre_id")
  @JsonIgnoreProperties({ "padre", "madre", "hijos", "enfermedades",
      "desarrollosCrecimiento", "produccion" })
  private Animal padre;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "madre_id")
  @JsonIgnoreProperties({ "padre", "madre", "hijos", "enfermedades",
      "desarrollosCrecimiento", "produccion" })
  private Animal madre;

  @OneToMany(mappedBy = "padre", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Animal> hijosPadre;

  @OneToMany(mappedBy = "madre", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Animal> hijosMadre;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "animal")
  @JsonIgnoreProperties({ "animal" }) // Prevent back reference in JSON
  private List<Enfermedad> enfermedades;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "establo_id")
  @JsonIgnoreProperties({ "animales", "hibernateLazyInitializer", "handler" })
  private Establo establo;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<DesarrolloCrecimiento> desarrollosCrecimiento;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<RegistroProduccion> produccion;
}
