package mx.edu.utez.gestionproyectos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.gestionproyectos.data.RetrofitClient
import mx.edu.utez.gestionproyectos.model.Project
import androidx.compose.runtime.*
import mx.edu.utez.gestionproyectos.data.SessionManager
import retrofit2.HttpException

data class ProjectWithProgress(
    val project: Project,
    val progress: Float
)

class ProjectViewModel : ViewModel() {

    var projects by mutableStateOf<List<ProjectWithProgress>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
    var errorMsg by mutableStateOf<String?>(null)

    fun loadProjects() {
        if (isLoading) return // Evitar múltiples llamadas simultáneas

        viewModelScope.launch {
            isLoading = true
            errorMsg = null
            
            println("DEBUG_PROJECTS: Iniciando carga...")
            println("DEBUG_PROJECTS: Token actual -> ${SessionManager.token}")

            if (SessionManager.token.isBlank()) {
                println("DEBUG_PROJECTS: ERROR - El token está vacío")
                errorMsg = "Sesión no válida"
                isLoading = false
                return@launch
            }

            try {
                val response = RetrofitClient.apiService.getProjects(
                    token = "Bearer ${SessionManager.token}"
                )
                
                println("DEBUG_PROJECTS: Status -> ${response.status}")
                println("DEBUG_PROJECTS: Cantidad de proyectos -> ${response.data?.size ?: 0}")

                if (response.status == "200 OK" || response.status == "success") {
                    val result = mutableListOf<ProjectWithProgress>()

                    response.data?.forEach { project ->
                        try {
                            val progressResponse = RetrofitClient.apiService.getProjectProgress(
                                token = "Bearer ${SessionManager.token}",
                                id = project.idProyecto
                            )
                            
                            val progress = if (progressResponse.status == "200 OK") {
                                progressResponse.data ?: 0f
                            } else {
                                0f
                            }

                            result.add(ProjectWithProgress(project = project, progress = progress))
                        } catch (e: Exception) {
                            println("DEBUG_PROJECTS: Error cargando progreso para ID ${project.idProyecto}")
                            result.add(ProjectWithProgress(project = project, progress = 0f))
                        }
                    }

                    projects = result
                    if (result.isEmpty()) {
                        println("DEBUG_PROJECTS: La lista de proyectos llegó vacía desde el servidor")
                    }
                } else {
                    errorMsg = "Error del servidor: ${response.status}"
                }

            } catch (e: HttpException) {
                println("DEBUG_PROJECTS: Error HTTP ${e.code()} -> ${e.message()}")
                errorMsg = "Error de servidor (${e.code()})"
            } catch (e: Exception) {
                e.printStackTrace()
                println("DEBUG_PROJECTS: Excepción -> ${e.localizedMessage}")
                errorMsg = "Error de conexión"
            } finally {
                isLoading = false
            }
        }
    }
}