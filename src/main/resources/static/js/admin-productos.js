// ================================
// 🔹 CONFIGURACIÓN GENERAL
// ================================
const API_URL = "http://localhost:8080/api/productos";
const API_SUCURSALES = "http://localhost:8080/api/catalogo/sucursales";
const API_CATEGORIAS = "http://localhost:8080/api/catalogo/categorias";
const token = localStorage.getItem("token");
let modalProducto;

// ================================
// 🔹 INICIALIZACIÓN
// ================================
document.addEventListener("DOMContentLoaded", () => {
    if (!token) {
        window.location.href = "/login";
        return;
    }

    modalProducto = new bootstrap.Modal(document.getElementById("modalProducto"));
    cargarCategorias();
    cargarSucursales();
    listarProductos();

    document.getElementById("btnNuevo").addEventListener("click", nuevoProducto);
    document.getElementById("productoForm").addEventListener("submit", guardarProducto);
});

// ================================
// 🔹 FUNCIONES CRUD
// ================================

// 🔸 Listar todos los productos
function listarProductos() {
    const tbody = document.querySelector("#tablaProductos tbody");
    tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted py-3">Cargando productos...</td></tr>`;

    fetch(API_URL, { headers: { "Authorization": `Bearer ${token}` } })
        .then(res => res.json())
        .then(data => {
            tbody.innerHTML = "";
            if (!data || data.length === 0) {
                tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted py-3">No hay productos registrados</td></tr>`;
                return;
            }

            data.forEach(prod => {
                const fila = document.createElement("tr");
                fila.innerHTML = `
          <td>${prod.id}</td>
          <td>${prod.nombre}</td>
          <td>${prod.categoria}</td>
          <td>${prod.sucursal}</td>
          <td>S/ ${prod.precio.toFixed(2)}</td>
          <td>
            <span class="badge ${prod.activo ? "bg-success" : "bg-secondary"}">
              ${prod.activo ? "Activo" : "Inactivo"}
            </span>
          </td>
          <td>
            <button class="btn btn-sm btn-warning me-1" title="Editar" onclick="editarProducto(${prod.id})">
              <i class="bi bi-pencil-square"></i>
            </button>
            <button class="btn btn-sm btn-danger" title="Eliminar" onclick="eliminarProducto(${prod.id})">
              <i class="bi bi-trash"></i>
            </button>
          </td>`;
                tbody.appendChild(fila);
            });
        })
        .catch(err => console.error("Error al listar productos:", err));
}

// 🔸 Crear o actualizar producto
function guardarProducto(e) {
    e.preventDefault();

    const id = document.getElementById("productoId").value;
    const producto = {
        nombre: document.getElementById("nombre").value.trim(),
        categoriaId: parseInt(document.getElementById("categoriaId").value),
        sucursalId: parseInt(document.getElementById("sucursalId").value),
        precio: parseFloat(document.getElementById("precio").value),
        activo: document.getElementById("activo").value === "true"
    };

    const method = id ? "PUT" : "POST";
    const url = id ? `${API_URL}/${id}` : API_URL;

    fetch(url, {
        method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(producto)
    })
        .then(res => {
            if (!res.ok) throw new Error("Error al guardar el producto");
            modalProducto.hide();
            listarProductos();
        })
        .catch(err => console.error("Error al guardar:", err));
}

// 🔸 Editar producto
function editarProducto(id) {
    fetch(`${API_URL}/${id}`, { headers: { "Authorization": `Bearer ${token}` } })
        .then(res => res.json())
        .then(prod => {
            document.getElementById("productoId").value = prod.id;
            document.getElementById("nombre").value = prod.nombre;
            document.getElementById("precio").value = prod.precio;
            document.getElementById("activo").value = prod.activo.toString();

            seleccionarOpcion("categoriaId", prod.categoria);
            seleccionarOpcion("sucursalId", prod.sucursal);

            document.getElementById("modalProductoLabel").textContent = "Editar Producto";
            modalProducto.show();
        })
        .catch(err => console.error("Error al editar:", err));
}

// 🔸 Eliminar producto
function eliminarProducto(id) {
    if (!confirm("¿Deseas eliminar este producto?")) return;

    fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    })
        .then(res => {
            if (!res.ok) throw new Error("Error al eliminar");
            listarProductos();
        })
        .catch(err => console.error("Error al eliminar:", err));
}

// ================================
// 🔹 FUNCIONES DE APOYO
// ================================

// Cargar categorías
function cargarCategorias() {
    fetch(API_CATEGORIAS)
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById("categoriaId");
            select.innerHTML = "";
            data.forEach(cat => {
                const option = document.createElement("option");
                option.value = cat.id;
                option.textContent = cat.nombre;
                select.appendChild(option);
            });
        })
        .catch(err => console.error("Error al cargar categorías:", err));
}

// Cargar sucursales
function cargarSucursales() {
    fetch(API_SUCURSALES)
        .then(res => res.json())
        .then(data => {
            const select = document.getElementById("sucursalId");
            select.innerHTML = "";
            data.forEach(suc => {
                const option = document.createElement("option");
                option.value = suc.id;
                option.textContent = suc.nombre;
                select.appendChild(option);
            });
        })
        .catch(err => console.error("Error al cargar sucursales:", err));
}

// Seleccionar valor en combo
function seleccionarOpcion(selectId, texto) {
    const select = document.getElementById(selectId);
    for (let opt of select.options) {
        if (opt.text === texto) {
            opt.selected = true;
            break;
        }
    }
}

// Nuevo producto
function nuevoProducto() {
    document.getElementById("productoForm").reset();
    document.getElementById("productoId").value = "";
    document.getElementById("modalProductoLabel").textContent = "Nuevo Producto";
    modalProducto.show();
}
