package mx.edu.utez.gestionproyectos.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.gestionproyectos.data.RetrofitClient
import mx.edu.utez.gestionproyectos.data.SessionManager
import mx.edu.utez.gestionproyectos.model.Task

class TaskViewModel : ViewModel() {

    var tasks = mutableStateListOf<Task>()

    fun loadTasks() {

        viewModelScope.launch {

            try {

                val token = SessionManager.token

                val response =
                    RetrofitClient.apiService.getMyTasks("Bearer $token")

                if (!response.error) {

                    tasks.clear()
                    response.data?.let {
                        tasks.addAll(it)
                    }

                }

            } catch (e: Exception) {

                e.printStackTrace()

            }

        }

    }

    fun updateStatus(id: Int, estado: String) {

        viewModelScope.launch {

            try {

                val token = SessionManager.token

                RetrofitClient.apiService.updateTaskStatus(
                    "Bearer $token",
                    id,
                    estado
                )

                // recargar tareas
                loadTasks()

            } catch (e: Exception) {

                e.printStackTrace()

            }

        }

    }

}