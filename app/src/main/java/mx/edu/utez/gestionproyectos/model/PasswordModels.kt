package mx.edu.utez.gestionproyectos.model

data class ForgotPasswordRequest(
    val correo: String
)

data class ResetPasswordRequest(
    val token: String,
    val newPassword: String
)

data class ValidateTokenResponse(
    val valido: Boolean,
    val correo: String,
    val username: String
)