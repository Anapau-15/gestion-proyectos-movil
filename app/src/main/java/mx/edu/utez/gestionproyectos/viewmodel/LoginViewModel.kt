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
                    RetrofitClient.apiService.login(LoginRequest(username, password))

                }

                println("DEBUG_LOGIN: JSON Recibido -> $response")

                // Comprobamos si el status es "200 OK" o si hay un token
                if (response.data?.token != null || response.mensaje == "Éxito" || response.status == "200 OK") {
                    SessionManager.token = response.data?.token ?: ""

                    println("TOKEN_GUARDADO: ${SessionManager.token}")
                    isSuccess = true
                } else {
                    errorMessage = response.mensaje ?: "Credenciales incorrectas"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("DEBUG_LOGIN: Error detectado: ${e.message}")
                errorMessage = "Error de conexión o de servidor"
            } finally {
                isLoading = false
            }
        }
    }
}