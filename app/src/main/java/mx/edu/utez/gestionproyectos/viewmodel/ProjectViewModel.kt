package mx.edu.utez.gestionproyectos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.gestionproyectos.data.RetrofitClient
import mx.edu.utez.gestionproyectos.model.Project
import androidx.compose.runtime.*
import mx.edu.utez.gestionproyectos.data.SessionManager

data class ProjectWithProgress(
    val project: Project,
    val progress: Float
)

class ProjectViewModel : ViewModel() {

    var projects by mutableStateOf<List<ProjectWithProgress>>(emptyList())
        private set

    fun loadProjects() {

        viewModelScope.launch {

            println("DEBUG: Cargando proyectos...")
            println("TOKEN: ${SessionManager.token}")
            println("HEADER: Bearer ${SessionManager.token}")
            try {

                val response = RetrofitClient.apiService.getProjects(
                    token = "Bearer ${SessionManager.token}"
                )
                println("RESPONSE: $response")
                println("DEBUG RESPONSE: $response")
                println("DEBUG DATA: ${response.data}")
                println("DEBUG SIZE: ${response.data?.size}")

                if (response.status == "200 OK") {

                    val result = mutableListOf<ProjectWithProgress>()

                    response.data?.forEach { project ->

                        val progressResponse =
                            RetrofitClient.apiService.getProjectProgress(
                                token = "Bearer ${SessionManager.token}",
                                id = project.idProyecto
                            )
                        println("PROGRESO PROYECTO ${project.idProyecto}: ${progressResponse.data}")

                        val progress = if (progressResponse.status == "200 OK") {
                            progressResponse.data ?: 0f
                        } else {
                            0f
                        }

                        result.add(
                            ProjectWithProgress(
                                project = project,
                                progress = progress
                            )
                        )
                    }

                    projects = result
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}