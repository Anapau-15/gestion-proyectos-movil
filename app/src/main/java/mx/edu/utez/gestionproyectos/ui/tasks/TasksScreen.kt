import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.gestionproyectos.ui.components.ScreenWithLoader
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader
import mx.edu.utez.gestionproyectos.ui.tasks.TaskCard
import mx.edu.utez.gestionproyectos.viewmodel.TaskViewModel

@Composable
fun TasksScreen(
    viewModel: TaskViewModel = viewModel()
) {
    val tasks = viewModel.tasks
    val loading = viewModel.loading

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // El Header se queda fijo arriba
        HomeHeader()

        // El Box con weight(1f) es el truco: obliga al resto del contenido
        // a ocupar el espacio sobrante y permite que la LazyColumn interna haga scroll.
        Box(modifier = Modifier.weight(1f)) {
            ScreenWithLoader(
                loading = loading,
                modifier = Modifier.fillMaxSize()
            ) {
                // Header de la lista
                item {
                    Text(
                        text = "Mis Tareas",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A3B5D)
                    )

                    Text(
                        text = "Tareas asignadas",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                    )
                }

                // Lista de tareas
                items(tasks) { task ->
                    TaskCard(
                        task = task,
                        onStatusChange = { estado ->
                            viewModel.updateStatus(task.idTarea, estado)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Espacio extra al final para que la última tarjeta no quede al ras
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}