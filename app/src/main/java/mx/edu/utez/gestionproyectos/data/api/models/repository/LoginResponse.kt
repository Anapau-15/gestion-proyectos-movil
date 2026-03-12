package mx.edu.utez.gestionproyectos.data.api.models.repository

data class LoginResponse(
    val token: String,
    val userId: Int,
    val name: String
)