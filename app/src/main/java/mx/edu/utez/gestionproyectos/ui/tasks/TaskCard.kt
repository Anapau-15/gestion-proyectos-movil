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
        Column(modifier = Modifier.padding(16.dp)) {
            // Título de la tarea
            Text(
                text = task.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A3B5D)
            )

            Spacer(modifier = Modifier.height(12.dp))
            // 🔑 Descripción de la tarea
            if (!task.descripcion.isNullOrBlank()) {
                Text(
                    text = task.descripcion,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Fila de Etiquetas (Prioridad y Estado)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Etiqueta Prioridad
                LabelWithText("Prioridad:", task.prioridad, Color(0xFFFFEBEE), Color(0xFFD32F2F))

                // Etiqueta Estado
                LabelWithText("Estado:", task.estado, Color(0xFFF5F5F5), Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fechas con iconos o texto simple
            DateRow("Inicio", task.fechaInicio)
            DateRow("Fin", task.fechaFin)

            Spacer(modifier = Modifier.height(20.dp))

            // Aquí va tu componente de botones
            TaskStateSection(
                estadoActual = task.estado,
                onStatusChange = onStatusChange
            )
        }
    }
}

@Composable
fun LabelWithText(label: String, value: String, bgColor: Color, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(end = 4.dp))
        Surface(
            color = bgColor,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = value,
                color = textColor,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
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