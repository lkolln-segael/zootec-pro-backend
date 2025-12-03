const sintomasForm = document.querySelectorAll('.sintomas-form');
const tratamientosForm = document.querySelectorAll('.tratamientos-form');


sintomasForm.forEach(sintomas => {
  const addSintoma = sintomas.querySelector(".add-sintoma");

  addSintoma.addEventListener("click", () => {
    const div = document.createElement("div")
    div.classList.add("sintoma-item");
    div.classList.add("mb-3");
    const labelNombre = document.createElement("label");
    labelNombre.classList.add("form-label");
    labelNombre.textContent = "Nombre del síntoma";
    labelNombre.setAttribute("for", "sintoma-nombre");
    const inputNombre = document.createElement("input");
    inputNombre.classList.add("form-control");
    inputNombre.setAttribute("type", "text");
    inputNombre.setAttribute("name", "sintoma-nombre");

    const labelDescripcion = document.createElement("label");
    labelDescripcion.classList.add("form-label");
    labelDescripcion.textContent = "Descripción del síntoma";
    labelDescripcion.setAttribute("for", "sintoma-descripcion");
    labelDescripcion.classList.add("mt-2");

    const inputDescripcion = document.createElement("input");
    inputDescripcion.classList.add("form-control");
    inputDescripcion.setAttribute("type", "text");
    inputDescripcion.setAttribute("name", "sintoma-descripcion");

    div.appendChild(labelNombre);
    div.appendChild(inputNombre);
    div.appendChild(labelDescripcion);
    div.appendChild(inputDescripcion);
    sintomas.appendChild(div)
  })

  sintomas.addEventListener("submit", async () => {
    const sintomasItems = sintomas.querySelectorAll(".sintoma-item");
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content') ||
      document.querySelector('input[name="_csrf"]')?.value;
    const sintomasModel = []
    sintomasItems.forEach(item => {
      const itemNombre = item.querySelector('input[name="sintoma-nombre"]').value;
      const itemDescripcion = item.querySelector('input[name="sintoma-descripcion"]').value;
      sintomasModel.push({
        nombre: itemNombre,
        descripcion: itemDescripcion
      })
    })
    const id = sintomas.getAttribute("data-id");
    await fetch("/admin/enfermedades/tipo/sintomas/" + id, {
      body: JSON.stringify(sintomasModel),
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "X-CSRF-TOKEN": csrfToken
      }
    })
    window.reload()
  })
})


tratamientosForm.forEach(tratamiento => {
  const addTratamiento = tratamiento.querySelector(".add-tratamiento");

  addTratamiento.addEventListener("click", () => {
    const div = document.createElement("div")
    div.classList.add("tratamiento-item");
    div.classList.add("mb-3");
    const labelNombre = document.createElement("label");
    labelNombre.classList.add("form-label");
    labelNombre.textContent = "Nombre del tratamiento";
    labelNombre.setAttribute("for", "tratamiento-nombre");
    const inputNombre = document.createElement("input");
    inputNombre.classList.add("form-control");
    inputNombre.setAttribute("type", "text");
    inputNombre.setAttribute("name", "tratamiento-nombre");

    const labelDescripcion = document.createElement("label");
    labelDescripcion.classList.add("form-label");
    labelDescripcion.textContent = "Descripción del tratamiento";
    labelDescripcion.setAttribute("for", "tratamiento-descripcion");
    labelDescripcion.classList.add("mt-2");

    const inputDescripcion = document.createElement("input");
    inputDescripcion.classList.add("form-control");
    inputDescripcion.setAttribute("type", "text");
    inputDescripcion.setAttribute("name", "tratamiento-descripcion");

    div.appendChild(labelNombre);
    div.appendChild(inputNombre);
    div.appendChild(labelDescripcion);
    div.appendChild(inputDescripcion);
    tratamiento.appendChild(div)
  })

  tratamiento.addEventListener("submit", async () => {
    const tratamientoItems = tratamiento.querySelectorAll(".tratamiento-item");
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content') ||
      document.querySelector('input[name="_csrf"]')?.value;
    const tratamientoModel = []
    tratamientoItems.forEach(item => {
      const itemNombre = item.querySelector('input[name="tratamiento-nombre"]').value;
      const itemDescripcion = item.querySelector('input[name="tratamiento-descripcion"]').value;
      tratamientoModel.push({
        nombre: itemNombre,
        descripcion: itemDescripcion
      })
    })
    const id = tratamiento.getAttribute("data-id");
    await fetch("/admin/enfermedades/tipo/tratamientos/" + id, {
      body: JSON.stringify(tratamientoModel),
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "X-CSRF-TOKEN": csrfToken
      }
    })
    window.reload()
  })
})
