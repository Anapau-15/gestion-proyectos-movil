package mx.edu.utez.gestionproyectos.ui.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.gestionproyectos.ui.components.ScreenWithLoader
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader
import mx.edu.utez.gestionproyectos.viewmodel.TaskViewModel

@Composable
fun TasksScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks = viewModel.tasks
    val loading = viewModel.loading


    var selectedFilter by remember { mutableStateOf("PENDIENTE") }


    val filteredTasks = tasks.filter { task ->
        val estadoDB = task.estado.uppercase()
        when (selectedFilter) {
            "PENDIENTE" -> estadoDB == "PENDIENTE"
            "PROGRESO"  -> estadoDB == "EN_PROGRESO" || estadoDB == "PROGRESO"
            "FINALIZADA" -> estadoDB == "COMPLETADA" || estadoDB == "TERMINADO"
            else -> true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        HomeHeader()

        Box(modifier = Modifier.weight(1f)) {
            ScreenWithLoader(loading = loading,
                modifier = Modifier.fillMaxSize(),) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            text = "Mis Tareas",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A3B5D)
                        )
                        Spacer(modifier = Modifier.height(17.dp))

                        // FILTROS PAREJOS CON PESO IGUAL
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                        ) {
                            FilterChip(selectedFilter == "PENDIENTE", "Pendientes", Modifier.weight(1f)) { selectedFilter = "PENDIENTE" }
                            FilterChip(selectedFilter == "PROGRESO", "Progreso", Modifier.weight(1f)) { selectedFilter = "PROGRESO" }
                            FilterChip(selectedFilter == "FINALIZADA", "Terminada", Modifier.weight(1f)) { selectedFilter = "FINALIZADA" }
                        }
                    }
                }

                // 3. MENSAJE DE LISTA VACÍA: Ahora sí aparecerá si no hay tareas filtradas
                if (filteredTasks.isEmpty() && !loading) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No hay tareas en estado ${selectedFilter.lowercase()}",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                items(filteredTasks) { task ->
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                        TaskCard(task = task, onStatusChange = { nuevoEstado ->
                            viewModel.updateStatus(task.idTarea, nuevoEstado)
                        })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Espacio extra al final para el scroll
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

// Componente para los botones de filtro (puedes ponerlo al final del mismo archivo)
@Composable
fun FilterChip(
    isSelected: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) Color(0xFF1A3B5D) else Color.White,
        shape = RoundedCornerShape(12.dp), // Esquinas un poco más cuadradas para que el texto quepa mejor
        border = BorderStroke(1.dp, if (isSelected) Color(0xFF1A3B5D) else Color.LightGray),
        modifier = modifier
            .height(48.dp) // ALTO FIJO PARA QUE SEAN PAREJOS
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                color = if (isSelected) Color.White else Color.Gray,
                fontSize = 12.sp, // Tamaño de fuente controlado
                fontWeight = FontWeight.SemiBold,
                maxLines = 1 // Evita que el texto se salte de línea
            )
        }
    }
}