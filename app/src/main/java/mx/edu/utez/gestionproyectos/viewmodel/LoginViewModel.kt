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
    var resetPasswordSuccess by mutableStateOf<String?>(null)

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

                println("DEBUG_LOGIN: $response")

                val token = response.data?.token

                if (!token.isNullOrEmpty()) {
                    SessionManager.token = token

                    val json = decodeJWT(token)

                    SessionManager.nombre = json.optString("nombre", "Usuario")
                    SessionManager.rol = json.optString("rol", "Sin rol")
                    SessionManager.correo = json.optString("sub", "sin@correo.com")

                    println("NOMBRE: ${SessionManager.nombre}")
                    println("ROL: ${SessionManager.rol}")
                    println("CORREO: ${SessionManager.correo}")

                    isSuccess = true
                } else {
                    errorMessage = response.mensaje ?: "Credenciales incorrectas"
                    println(" CREDENCIALES INCORRECTAS: ${response.mensaje}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = when {
                    e.message?.contains("Network") == true || e.message?.contains("timeout") == true ->
                        "Error de conexión. Verifica tu internet"
                    e.message?.contains("401") == true || e.message?.contains("403") == true ->
                        "Credenciales incorrectas"
                    else ->
                        "Error del servidor. Intenta de nuevo"
                }
                println(" ERROR: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    //  FORGOT PASSWORD
    var forgotPasswordLoading by mutableStateOf(false)
    var forgotPasswordError by mutableStateOf<String?>(null)
    var forgotPasswordSuccess by mutableStateOf<String?>(null)
    var userEmail by mutableStateOf<String?>(null)

    fun forgotPassword(correo: String) {
        viewModelScope.launch {
            forgotPasswordLoading = true
            forgotPasswordError = null
            forgotPasswordSuccess = null

            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.forgotPassword(
                        mx.edu.utez.gestionproyectos.model.ForgotPasswordRequest(correo = correo)
                    )
                }

                if (response.status == "200 OK" || response.status == "success") {
                    forgotPasswordSuccess = "Revisa tu correo para el código de recuperación"
                    userEmail = correo
                } else {
                    //  MEJORAR MENSAJES DE ERROR
                    forgotPasswordError = when {
                        response.message?.contains("no existe", ignoreCase = true) == true -> "La cuenta no existe"
                        response.message?.contains("no encontrado", ignoreCase = true) == true -> "La cuenta no existe"
                        else -> response.message ?: "Error al buscar la cuenta"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //  MEJORAR MENSAJE DE EXCEPCIONES
                forgotPasswordError = when {
                    e.message?.contains("400") == true -> "La cuenta no existe"
                    e.message?.contains("404") == true -> "La cuenta no existe"
                    e.message?.contains("Network") == true -> "Error de conexión"
                    e.message?.contains("timeout") == true -> "Tiempo de espera agotado"
                    else -> "Error: ${e.message}"
                }
            } finally {
                forgotPasswordLoading = false
            }
        }
    }

    //  VALIDATE TOKEN
    var tokenValido by mutableStateOf(false)
    var resetToken by mutableStateOf<String?>(null)

    fun validateResetToken(tokenValue: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.validateResetToken(tokenValue)
                }

                if (response.status == "200 OK" || response.status == "success") {
                    resetToken = tokenValue
                    tokenValido = true
                } else {
                    //  MEJORAR MENSAJE DE ERROR
                    errorMessage = when {
                        response.message?.contains("expirado", ignoreCase = true) == true -> "Código expirado. Solicita uno nuevo"
                        response.message?.contains("inválido", ignoreCase = true) == true -> "Código inválido"
                        response.message?.contains("no encontrado", ignoreCase = true) == true -> "Código no encontrado"
                        else -> response.message ?: "Código inválido o expirado"
                    }
                    tokenValido = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //  MEJORAR MENSAJE DE ERROR EN EXCEPCIONES
                errorMessage = when {
                    e.message?.contains("400") == true -> "Código inválido o expirado"
                    e.message?.contains("401") == true -> "No autorizado"
                    e.message?.contains("404") == true -> "Código no encontrado"
                    e.message?.contains("Network") == true -> "Error de conexión"
                    e.message?.contains("timeout") == true -> "Tiempo de espera agotado"
                    else -> "Error: ${e.message}"
                }
                tokenValido = false
            } finally {
                isLoading = false
            }
        }
    }

    // RESET PASSWORD
    fun resetPassword(newPassword: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            resetPasswordSuccess = null

            if (resetToken == null) {
                errorMessage = "Token no encontrado"
                isLoading = false
                return@launch
            }

            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.resetPassword(
                        mx.edu.utez.gestionproyectos.model.ResetPasswordRequest(
                            token = resetToken!!,
                            newPassword = newPassword
                        )
                    )
                }

                if (response.status == "200 OK" || response.status == "success") {
                    resetPasswordSuccess = "Contraseña reestablecida correctamente"
                    resetToken = null
                    userEmail = null
                    tokenValido = false
                } else {
                    // 🔥 MEJORAR MENSAJES DE ERROR
                    errorMessage = when {
                        response.message?.contains("requisito", ignoreCase = true) == true -> "La contraseña no cumple los requisitos"
                        response.message?.contains("8 caracteres", ignoreCase = true) == true -> "La contraseña no cumple los requisitos"
                        response.message?.contains("mayúscula", ignoreCase = true) == true -> "La contraseña no cumple los requisitos"
                        response.message?.contains("minúscula", ignoreCase = true) == true -> "La contraseña no cumple los requisitos"
                        response.message?.contains("número", ignoreCase = true) == true -> "La contraseña no cumple los requisitos"
                        response.message?.contains("especial", ignoreCase = true) == true -> "La contraseña no cumple los requisitos"
                        else -> response.message ?: "Error al actualizar contraseña"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // 🔥 MEJORAR MENSAJE DE EXCEPCIONES
                errorMessage = when {
                    e.message?.contains("400") == true -> "La contraseña no cumple los requisitos"
                    e.message?.contains("401") == true -> "No autorizado"
                    e.message?.contains("Network") == true -> "Error de conexión"
                    e.message?.contains("timeout") == true -> "Tiempo de espera agotado"
                    else -> "Error: ${e.message}"
                }
            } finally {
                isLoading = false
            }
        }
    }



    fun clearMessages() {
        errorMessage = null
        forgotPasswordError = null
        forgotPasswordSuccess = null
        resetPasswordSuccess = null
    }
}