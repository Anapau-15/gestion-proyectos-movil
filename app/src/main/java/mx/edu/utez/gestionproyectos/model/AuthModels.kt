package mx.edu.utez.gestionproyectos.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @SerializedName("data")
    val data: LoginData?,

    @SerializedName("text") 
    val mensaje: String?,

    @SerializedName("status")
    val status: String? // Cambiado de Int? a String? para aceptar "200 OK"
)

data class LoginData(
    @SerializedName("token")
    val token: String?
)