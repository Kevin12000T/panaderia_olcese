// ==============================
// main.js (versión corta)
// ==============================

// Verifica token al cargar la página
document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("authToken");
    if (!token) {
        window.location.href = "/login";
    }
});

// Peticiones con token
async function fetchConToken(url, options = {}) {
    const token = localStorage.getItem("authToken");
    if (!token) {
        window.location.href = "/login";
        return;
    }

    options.headers = {
        ...options.headers,
        "Authorization": "Bearer " + token,
        "Content-Type": "application/json"
    };

    const resp = await fetch(url, options);

    // Si el token expira o no es válido
    if (resp.status === 401 || resp.status === 403) {
        localStorage.clear();
        window.location.href = "/login";
    }

    return resp;
}

// Cerrar sesión manualmente
function logout() {
    localStorage.clear();
    window.location.href = "/login";
}
