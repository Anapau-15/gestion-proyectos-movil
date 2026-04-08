package mx.edu.utez.gestionproyectos.ui.tasks

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TaskStateSection(
    estadoActual: String, // "PENDIENTE", "EN_PROGRESO", "TERMINADO"
    onStatusChange: (String) -> Unit
) {
    Column {
        Text(
            text = "Cambiar estado:",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 1. Si es PENDIENTE, solo mostramos el texto (no botón)
            if (estadoActual == "PENDIENTE") {
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("Pendiente", color = Color.Gray, fontWeight = FontWeight.Bold)
                }
            }

            // 2. Botón "En progreso"
            // Se habilita SOLO si el estado es PENDIENTE
            Button(
                onClick = { onStatusChange("EN_PROGRESO") },
                enabled = estadoActual == "PENDIENTE",
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5), // Azul de tu captura
                    disabledContainerColor = if (estadoActual == "EN_PROGRESO") Color(0xFF3F51B5).copy(alpha = 0.5f) else Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("En progreso", color = Color.White)
            }

            // 3. Botón "Terminado"
            // Se muestra o habilita SOLO si ya está "EN_PROGRESO"
            if (estadoActual != "TERMINADO") {
                Button(
                    onClick = { onStatusChange("TERMINADO") },
                    enabled = estadoActual == "EN_PROGRESO",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF76A379), // Verde de tu captura
                        disabledContainerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Terminado", color = Color.White)
                }
            } else {
                // 4. Si ya está TERMINADO, mostramos solo la etiqueta final
                Text(
                    text = "Estado: Terminado ✅",
                    color = Color(0xFF76A379),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}