document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const data = {
        nombre: document.getElementById("nombre").value.trim(),
        apellido: document.getElementById("apellido").value.trim(),
        email: document.getElementById("email").value.trim(),
        telefono: document.getElementById("telefono").value.trim(),
        password: document.getElementById("password").value.trim()
    };

    try {
        const response = await fetch("/public/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert("Registro exitoso ✅. Ahora inicia sesión.");
            window.location.href = "/login";
        } else {
            const error = await response.text();
            alert("❌ Error al registrar: " + error);
        }
    } catch (err) {
        alert("⚠️ Error de conexión con el servidor.");
        console.error(err);
    }
});
