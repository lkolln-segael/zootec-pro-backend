package zootecpro.backend.models.dto;

public record DesarrolloCrecimientoForm(
    String animalId,
    String estado,
    Integer pesoActual,
    Double tamaño,
    String condicionCorporal,
    String unidadesAnimal) {

}
