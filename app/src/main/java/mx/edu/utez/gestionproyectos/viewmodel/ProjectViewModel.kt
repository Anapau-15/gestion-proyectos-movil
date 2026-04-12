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
        if (isLoading) return

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
                println("DEBUG_PROJECTS: RESPUESTA COMPLETA -> $response")
                println("DEBUG_PROJECTS: LISTA DE DATOS -> ${response.data}")

                if (response.status == "200 OK" || response.status == "success") {
                    val result = mutableListOf<ProjectWithProgress>()

                    response.data?.forEach { project ->
                        println("DEBUG_JSON_PROJECT: $project")

                        var progress = 0f
                        try {
                            // 🔑 CORREGIR: Obtener tareas del proyecto en lugar de progreso
                            val tasksResponse = RetrofitClient.apiService.getProjectTasks(
                                token = "Bearer ${SessionManager.token}",
                                id = project.idProyecto
                            )

                            if (tasksResponse.status == "200 OK" || tasksResponse.status == "success") {
                                val tareas = tasksResponse.data ?: emptyList()

                                println("DEBUG_TASKS: Proyecto ${project.nombre} (ID: ${project.idProyecto})")
                                println("DEBUG_TASKS: Total de tareas -> ${tareas.size}")

                                if (tareas.isNotEmpty()) {
                                    // Contar tareas completadas: TERMINADO + COMPLETADA
                                    val completadas = tareas.count { tarea ->
                                        val estado = tarea.estado?.uppercase() ?: ""
                                        estado == "TERMINADO" || estado == "COMPLETADA"
                                    }

                                    // Calcular progreso: (completadas / total) * 100
                                    progress = completadas.toFloat() / tareas.size
                                    println("DEBUG_TASKS: Tareas completadas -> $completadas")
                                    println("DEBUG_TASKS: Progreso calculado -> ${progress.toInt()}%")
                                } else {
                                    progress = 0f
                                    println("DEBUG_TASKS: Sin tareas")
                                }
                            } else {
                                println("DEBUG_TASKS: Error obteniendo tareas para proyecto ${project.idProyecto}")
                                progress = 0f
                            }
                        } catch (e: Exception) {
                            println("DEBUG_TASKS: Excepción obteniendo tareas -> ${e.localizedMessage}")
                            e.printStackTrace()
                            progress = 0f
                        }

                        result.add(ProjectWithProgress(project = project, progress = progress))
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