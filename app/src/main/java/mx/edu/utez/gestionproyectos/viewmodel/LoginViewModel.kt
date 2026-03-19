package mx.edu.utez.gestionproyectos.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.edu.utez.gestionproyectos.data.RetrofitClient
import mx.edu.utez.gestionproyectos.data.SessionManager
import mx.edu.utez.gestionproyectos.model.LoginRequest
import mx.edu.utez.gestionproyectos.data.utils.decodeJWT

class LoginViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false)

    fun login(username: String, password: String) {

        if (username.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, llena todos los campos"
            return
        }

        viewModelScope.launch {

            isLoading = true
            errorMessage = null

            try {

                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.login(
                        LoginRequest(username, password)
                    )
                }

                println("DEBUG_LOGIN: $response")

                val token = response.data?.token

                if (!token.isNullOrEmpty()) {

                    SessionManager.token = token

                    val json = decodeJWT(token)

                    SessionManager.nombre = json.optString("nombre", "Usuario")
                    SessionManager.rol = json.optString("rol", "Sin rol")

                    // ⚠️ TEMPORAL (porque no tienes correo real)
                    SessionManager.correo = json.optString("sub", "usuario")

                    println("NOMBRE: ${SessionManager.nombre}")
                    println("ROL: ${SessionManager.rol}")
                    println("CORREO: ${SessionManager.correo}")

                    isSuccess = true
                } else {
                    errorMessage = response.mensaje ?: "Credenciales incorrectas"
                }

            } catch (e: Exception) {

                e.printStackTrace()
                errorMessage = "Error de conexión o servidor"

            } finally {

                isLoading = false

            }

        }
    }
}