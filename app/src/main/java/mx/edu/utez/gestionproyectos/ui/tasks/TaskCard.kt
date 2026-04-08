package mx.edu.utez.gestionproyectos.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.gestionproyectos.model.Task

@Composable
fun TaskCard(
    task: Task,
    onStatusChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Prioridad: ", color = Color.Gray, fontSize = 14.sp)
            Surface(
                color = Color(0xFFFFEBEE),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = task.prioridad,
                    color = Color(0xFFD32F2F),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text("Estado: ", color = Color.Gray, fontSize = 14.sp)
            Surface(
                color = Color(0xFFE0E0E0), // Gris claro como la imagen
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = task.estado,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun DateRow(label: String, date: String?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label: ",
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            text = date ?: "---",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}