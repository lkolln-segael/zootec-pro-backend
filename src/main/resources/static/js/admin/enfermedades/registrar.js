document.addEventListener('DOMContentLoaded', function () {
  let tratamientoCount = 1;

  // Función para obtener la fecha actual en formato datetime-local
  function getFechaActual() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  }

  // Establecer fecha actual en los campos al cargar la página
  const fechaActual = getFechaActual();

  // Configurar fecha de diagnóstico
  const fechaDiagnosticoInput = document.getElementById('fechaDiagnostico');
  if (fechaDiagnosticoInput) {
    fechaDiagnosticoInput.value = fechaActual;
    fechaDiagnosticoInput.max = fechaActual; // No permitir fechas futuras
  }

  // Configurar fecha inicio del primer tratamiento
  const primerFechaInicio = document.querySelector('input[name="tratamientos[0].fechaInicio"]');
  if (primerFechaInicio) {
    primerFechaInicio.value = fechaActual;
  }

  // Agregar tratamiento
  document.getElementById('agregarTratamiento').addEventListener('click', function () {
    tratamientoCount++;
    const container = document.getElementById('tratamientosContainer');

    const newTratamiento = document.createElement('div');
    newTratamiento.className = 'card mb-3 tratamiento-card';
    newTratamiento.innerHTML = `
          <div class="card-header bg-light">
            <div class="d-flex justify-content-between align-items-center">
              <span class="fw-bold">Tratamiento #${tratamientoCount}</span>
              <button type="button" class="btn btn-sm btn-outline-danger eliminarTratamiento">
                <i class="fas fa-trash"></i>
              </button>
            </div>
          </div>
          <div class="card-body">
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="tratamientos_${tratamientoCount - 1}_descripcion" class="form-label">Descripción <span class="text-danger">*</span></label>
                <input type="text" class="form-control" name="tratamientos[${tratamientoCount - 1}].descripcion" required
                       placeholder="Ej: Antibiótico por 7 días, Desparasitante">
              </div>
              <div class="col-md-6 mb-3">
                <label for="tratamientos_${tratamientoCount - 1}_medicamento" class="form-label">Medicamento</label>
                <input type="text" class="form-control" name="tratamientos[${tratamientoCount - 1}].medicamento"
                       placeholder="Ej: Penicilina, Ivermectina">
              </div>
              <div class="col-md-6 mb-3">
                <label for="tratamientos_${tratamientoCount - 1}_fechaInicio" class="form-label">Fecha Inicio <span class="text-danger">*</span></label>
                <input type="datetime-local" class="form-control" name="tratamientos[${tratamientoCount - 1}].fechaInicio" required
                       value="${fechaActual}">
              </div>
              <div class="col-md-6 mb-3">
                <label for="tratamientos_${tratamientoCount - 1}_fechaFin" class="form-label">Fecha Fin Estimada</label>
                <input type="datetime-local" class="form-control" name="tratamientos[${tratamientoCount - 1}].fechaFin">
              </div>
              <div class="col-md-12 mb-3">
                <label for="tratamientos_${tratamientoCount - 1}_dosis" class="form-label">Dosis y Frecuencia</label>
                <textarea class="form-control" name="tratamientos[${tratamientoCount - 1}].dosis" rows="2"
                          placeholder="Ej: 5ml cada 12 horas por 7 días"></textarea>
              </div>
            </div>
          </div>
        `;

    container.appendChild(newTratamiento);

    // Configurar fecha mínima para fechaFin
    const fechaInicioInput = newTratamiento.querySelector(`input[name="tratamientos[${tratamientoCount - 1}].fechaInicio"]`);
    const fechaFinInput = newTratamiento.querySelector(`input[name="tratamientos[${tratamientoCount - 1}].fechaFin"]`);

    if (fechaInicioInput && fechaFinInput) {
      fechaInicioInput.addEventListener('change', function () {
        fechaFinInput.min = this.value;
      });
    }
  });

  // Eliminar tratamiento (event delegation)
  document.getElementById('tratamientosContainer').addEventListener('click', function (e) {
    if (e.target.closest('.eliminarTratamiento')) {
      const tratamientoCard = e.target.closest('.tratamiento-card');
      if (tratamientoCard && document.querySelectorAll('.tratamiento-card').length > 1) {
        tratamientoCard.remove();
        // Renumerar los tratamientos restantes
        renumerarTratamientos();
      } else {
        alert('Debe haber al menos un tratamiento registrado');
      }
    }
  });

  function renumerarTratamientos() {
    const cards = document.querySelectorAll('.tratamiento-card');
    tratamientoCount = cards.length;

    cards.forEach((card, index) => {
      const header = card.querySelector('.card-header span');
      if (header) {
        header.textContent = `Tratamiento #${index + 1}`;
      }

      // Actualizar los nombres de los campos
      const inputs = card.querySelectorAll('input, textarea, select');
      inputs.forEach(input => {
        const name = input.getAttribute('name');
        if (name) {
          const newName = name.replace(/tratamientos\[\d+\]/, `tratamientos[${index}]`);
          input.setAttribute('name', newName);

          // Actualizar el id
          const id = input.getAttribute('id');
          if (id) {
            const newId = id.replace(/tratamientos_\d+_/, `tratamientos_${index}_`);
            input.setAttribute('id', newId);
          }
        }
      });

      // Actualizar los labels
      const labels = card.querySelectorAll('label');
      labels.forEach(label => {
        const forAttr = label.getAttribute('for');
        if (forAttr) {
          const newFor = forAttr.replace(/tratamientos_\d+_/, `tratamientos_${index}_`);
          label.setAttribute('for', newFor);
        }
      });
    });
  }

  // Configurar fechas mínimas para fechas de tratamiento
  document.querySelectorAll('input[name$=".fechaInicio"]').forEach(input => {
    input.addEventListener('change', function () {
      const name = this.getAttribute('name');
      const baseName = name.substring(0, name.lastIndexOf('.fechaInicio'));
      const fechaFinInput = document.querySelector(`input[name="${baseName}.fechaFin"]`);
      if (fechaFinInput) {
        fechaFinInput.min = this.value;
      }
    });
  });

  // Validación del formulario
  const enfermedadForm = document.getElementById('enfermedadForm');
  const submitButton = document.getElementById('submitButton');

  if (enfermedadForm) {
    enfermedadForm.addEventListener('submit', function (e) {
      let isValid = true;
      let errorMessage = '';

      // Validar nombre
      const nombre = document.getElementById('nombre').value.trim();
      if (!nombre) {
        isValid = false;
        errorMessage = 'El nombre de la enfermedad es requerido';
      }

      // Validar tipo de enfermedad
      const tipoEnfermedad = document.getElementById('tipoEnfermedad').value;
      if (!tipoEnfermedad) {
        isValid = false;
        errorMessage = 'Debe seleccionar un tipo de enfermedad';
      }

      // Validar fecha de diagnóstico
      const fechaDiagnostico = document.getElementById('fechaDiagnostico').value;
      if (!fechaDiagnostico) {
        isValid = false;
        errorMessage = 'La fecha de diagnóstico es requerida';
      }

      // Validar tratamientos
      const tratamientos = document.querySelectorAll('.tratamiento-card');
      let tratamientoValido = true;

      tratamientos.forEach((tratamiento, index) => {
        const descripcion = tratamiento.querySelector('input[name$=".descripcion"]').value.trim();
        const fechaInicio = tratamiento.querySelector('input[name$=".fechaInicio"]').value;

        if (!descripcion) {
          tratamientoValido = false;
          errorMessage = `La descripción del tratamiento #${index + 1} es requerida`;
        }

        if (!fechaInicio) {
          tratamientoValido = false;
          errorMessage = `La fecha de inicio del tratamiento #${index + 1} es requerida`;
        }

        const fechaFin = tratamiento.querySelector('input[name$=".fechaFin"]').value;
        if (fechaFin && new Date(fechaFin) < new Date(fechaInicio)) {
          tratamientoValido = false;
          errorMessage = `La fecha fin no puede ser anterior a la fecha inicio en el tratamiento #${index + 1}`;
        }
      });

      if (!tratamientoValido) {
        isValid = false;
      }

      if (!isValid) {
        e.preventDefault();
        alert('Error: ' + errorMessage);
      } else {
        // Cambiar texto del botón mientras se procesa
        submitButton.disabled = true;
        submitButton.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Guardando...';
      }
    });
  }

  // Mostrar advertencia si se intenta salir sin guardar
  let formChanged = false;
  const formInputs = document.querySelectorAll('#enfermedadForm input, #enfermedadForm textarea, #enfermedadForm select');

  formInputs.forEach(input => {
    input.addEventListener('change', function () {
      formChanged = true;
    });

    input.addEventListener('input', function () {
      formChanged = true;
    });
  });

  window.addEventListener('beforeunload', function (e) {
    if (formChanged) {
      e.preventDefault();
      e.returnValue = 'Tienes cambios sin guardar. ¿Seguro que quieres salir?';
      return e.returnValue;
    }
  });
});

const tipoEnfermedadSelect = document.getElementById('tipoEnfermedad');

tipoEnfermedadSelect.addEventListener('change', async function () {
  if (tipoEnfermedadSelect.value === "") return;
  const tratamientoSelect = document.getElementById("tratamiento")
  tratamientoSelect.innerHTML = ""
  const response = await fetch("/admin/enfermedades/tipo/tratamientos?tipoId=" + tipoEnfermedadSelect.value, {
    headers: { "Cache-Control": "public, max-age=3600" }
  })
  const tratamientos = await response.json()
  tratamientos.forEach(tratamiento => {
    tratamientoSelect.innerHTML += `<option value="${tratamiento.id}">${tratamiento.nombre}</option>`
  })
})
