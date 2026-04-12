package mx.edu.utez.gestionproyectos.ui.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskStateSection(
    estadoActual: String,
    onStatusChange: (String) -> Unit
) {
    // Definimos los colores exactos de tu captura
    val colorAzul = Color(0xFF3F51B5) // El azul de "En progreso"
    val colorVerde = Color(0xFF76A379) // El verde de "Terminado"
    val colorGrisDeshabilitado = Color(0xFFF1F1F1) // Gris muy claro para deshabilitado

    Column {
        Text(
            text = "Cambiar estado:",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Fila que contiene los tres espacios iguales
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // --- 1. Espacio para PENDIENTE (Ya no es botón) ---


            // --- 2. Botón EN PROGRESO (Azul) ---

            Box(modifier = Modifier.weight(1f)) {
                Button(
                    onClick = { onStatusChange("EN_PROGRESO") },
                    // Solo habilitado si el estado actual es PENDIENTE
                    enabled = estadoActual.uppercase() == "PENDIENTE" ,
                    // estadoActual.uppercase() == "COMPLETADA" ||
                           // estadoActual.uppercase() == "TERMINADO",
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(), // Llena su tercio
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorAzul, // Azul cuando está activo
                        contentColor = Color.White,
                        // Gris claro cuando está deshabilitado
                        disabledContainerColor = colorGrisDeshabilitado,
                        disabledContentColor = Color.Gray
                    ),
                    contentPadding = PaddingValues(0.dp) // Quitamos padding interno para centrar bien
                ) {
                    Text(
                        text = "En progreso",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // --- 3. Botón TERMINADO (Verde) ---
            // Usamos weight(1f) para ocupar 1/3 del ancho
            Box(modifier = Modifier.weight(1f)) {
                Button(
                    onClick = { onStatusChange("COMPLETADA") },
                    // Solo habilitado si el estado actual es EN_PROGRESO
                    enabled = estadoActual.uppercase() == "EN_PROGRESO",
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(), // Llena su tercio
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorVerde, // Verde cuando está activo
                        contentColor = Color.White,
                        // Gris claro cuando está deshabilitado
                        disabledContainerColor = colorGrisDeshabilitado,
                        disabledContentColor = Color.Gray
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Terminado",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}