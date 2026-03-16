package mx.edu.utez.gestionproyectos.data

import mx.edu.utez.gestionproyectos.model.Deposit
import mx.edu.utez.gestionproyectos.model.LoginRequest
import mx.edu.utez.gestionproyectos.model.LoginResponse
import mx.edu.utez.gestionproyectos.model.Project
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse


    @GET("proyectos/mis-proyectos")
    suspend fun getProjects(
        @Header("Authorization") token: String
    ): ApiResponse<List<Project>>


    @GET("proyectos/{id}/progreso")
    suspend fun getProjectProgress(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ApiResponse<Float>


    @GET("pagos/mis-pagos")
    suspend fun getDeposits(
        @Header("Authorization") token: String
    ): ApiResponse<List<Deposit>>

}