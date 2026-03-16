package mx.edu.utez.gestionproyectos.data

import mx.edu.utez.gestionproyectos.model.LoginRequest
import mx.edu.utez.gestionproyectos.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

}