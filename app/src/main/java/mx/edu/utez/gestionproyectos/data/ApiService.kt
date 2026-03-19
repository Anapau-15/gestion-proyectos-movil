package mx.edu.utez.gestionproyectos.data

import mx.edu.utez.gestionproyectos.model.*
import retrofit2.http.*

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


    @GET("tareas/mis-tareas")
    suspend fun getMyTasks(
        @Header("Authorization") token: String
    ): ApiResponse<List<Task>>


    @PATCH("tareas/{id}/estado")
    suspend fun updateTaskStatus(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("estado") estado: String
    ): ApiResponse<String>

    @GET("usuarios/mi-perfil")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): ApiResponse<User>

}