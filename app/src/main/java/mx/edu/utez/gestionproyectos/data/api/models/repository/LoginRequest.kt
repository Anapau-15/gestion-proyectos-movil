package mx.edu.utez.gestionproyectos.data.api.models.repository

data class LoginRequest(
    val email: String,
    val password: String
)