package mx.edu.utez.gestionproyectos.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.gestionproyectos.data.RetrofitClient
import mx.edu.utez.gestionproyectos.data.SessionManager
import mx.edu.utez.gestionproyectos.model.Task

class TaskViewModel : ViewModel() {

    var tasks = mutableStateListOf<Task>()
    var loading by mutableStateOf(true)

    fun loadTasks() {
        viewModelScope.launch {
            loading = true // Aseguramos que empiece cargando
            try {
                val token = SessionManager.token
                val response = RetrofitClient.apiService.getMyTasks("Bearer $token")

                if (!response.error) {
                    tasks.clear()
                    response.data?.let {
                        tasks.addAll(it)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                loading = false
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