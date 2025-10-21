const API_URL = "http://localhost:8080/api/sucursales";
const token = localStorage.getItem("token");
let modalSucursal;

document.addEventListener("DOMContentLoaded", () => {
    if (!token) {
        window.location.href = "/login";
        return;
    }

    modalSucursal = new bootstrap.Modal(document.getElementById("modalSucursal"));
    listarSucursales();

    document.getElementById("btnNueva").addEventListener("click", nuevaSucursal);
    document.getElementById("sucursalForm").addEventListener("submit", guardarSucursal);
});

// 🔹 Mostrar alertas Bootstrap
function mostrarAlerta(mensaje, tipo = "success") {
    const contenedor = document.getElementById("alertContainer");
    contenedor.innerHTML = `
        <div class="alert alert-${tipo} alert-dismissible fade show mt-3" role="alert">
            ${mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `;
    setTimeout(() => {
        const alerta = bootstrap.Alert.getOrCreateInstance(document.querySelector(".alert"));
        alerta.close();
    }, 4000);
}

// 🔹 Listar sucursales
function listarSucursales() {
    const tbody = document.querySelector("#tablaSucursales tbody");
    tbody.innerHTML = `<tr><td colspan="6" class="text-center text-muted py-3">Cargando sucursales...</td></tr>`;

    fetch(API_URL, { headers: { "Authorization": `Bearer ${token}` } })
        .then(res => res.json())
        .then(data => {
            tbody.innerHTML = "";
            if (!data.length) {
                tbody.innerHTML = `<tr><td colspan="6" class="text-muted py-3">No hay sucursales registradas</td></tr>`;
                return;
            }

            data.forEach(s => {
                const fila = `
                    <tr>
                        <td>${s.id}</td>
                        <td>${s.nombre}</td>
                        <td>${s.distrito}</td>
                        <td>${s.direccion}</td>
                        <td>${s.telefono}</td>
                        <td>
                            <button class="btn btn-sm btn-warning me-1" onclick="editarSucursal(${s.id})">
                                <i class="bi bi-pencil-square"></i>
                            </button>
                            <button class="btn btn-sm btn-danger" onclick="eliminarSucursal(${s.id})">
                                <i class="bi bi-trash"></i>
                            </button>
                        </td>
                    </tr>`;
                tbody.innerHTML += fila;
            });
        })
        .catch(err => console.error("Error al listar sucursales:", err));
}

// 🔹 Crear o actualizar sucursal
function guardarSucursal(e) {
    e.preventDefault();

    const id = document.getElementById("sucursalId").value;
    const sucursal = {
        nombre: document.getElementById("nombre").value.trim(),
        distrito: document.getElementById("distrito").value.trim(),
        direccion: document.getElementById("direccion").value.trim(),
        telefono: document.getElementById("telefono").value.trim()
    };

    const method = id ? "PUT" : "POST";
    const url = id ? `${API_URL}/${id}` : API_URL;

    fetch(url, {
        method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(sucursal)
    })
        .then(res => res.ok ? res.json() : res.text().then(msg => Promise.reject(msg)))
        .then(() => {
            modalSucursal.hide();
            listarSucursales();
            mostrarAlerta("Sucursal guardada correctamente", "success");
        })
        .catch(err => mostrarAlerta(err || "Error al guardar sucursal", "danger"));
}

// 🔹 Editar sucursal
function editarSucursal(id) {
    fetch(`${API_URL}/${id}`, { headers: { "Authorization": `Bearer ${token}` } })
        .then(res => res.json())
        .then(s => {
            document.getElementById("sucursalId").value = s.id;
            document.getElementById("nombre").value = s.nombre;
            document.getElementById("distrito").value = s.distrito;
            document.getElementById("direccion").value = s.direccion;
            document.getElementById("telefono").value = s.telefono;

            document.getElementById("modalSucursalLabel").textContent = "Editar Sucursal";
            modalSucursal.show();
        })
        .catch(err => mostrarAlerta("Error al editar sucursal: " + err, "danger"));
}

// 🔹 Eliminar sucursal
function eliminarSucursal(id) {
    if (!confirm("¿Eliminar esta sucursal?")) return;

    fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    })
        .then(res => {
            if (!res.ok) return res.text().then(msg => Promise.reject(msg));
            mostrarAlerta("Sucursal eliminada correctamente", "success");
            listarSucursales();
        })
        .catch(err => mostrarAlerta(err || "No se pudo eliminar la sucursal", "danger"));
}

// 🔹 Nueva sucursal
function nuevaSucursal() {
    document.getElementById("sucursalForm").reset();
    document.getElementById("sucursalId").value = "";
    document.getElementById("modalSucursalLabel").textContent = "Nueva Sucursal";
    modalSucursal.show();
}
