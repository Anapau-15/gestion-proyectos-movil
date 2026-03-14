package mx.edu.utez.gestionproyectos.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader

@Composable
fun TasksScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        HomeHeader()

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
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

            TaskCard()
        }
    }
}

@Composable
fun TaskCard() {
    // Estado para controlar cuál botón está seleccionado
    var selectedStatus by remember { mutableStateOf("En progreso") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Implementar sistema de autenticación",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A3B5D),
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Prioridad: ", color = Color.Gray)
                Surface(
                    color = Color(0xFFFFEBEE),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Alta",
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Text(text = "Estado: ", color = Color.Gray)
                
                // Color dinámico según el estado seleccionado
                val statusColor = when (selectedStatus) {
                    "Pendiente" -> Color(0xFF6B7280)
                    "En progreso" -> Color(0xFF3B4BF6)
                    "Terminado" -> Color(0xFF35904C)
                    else -> Color.Gray
                }

                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = selectedStatus,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            DateRow(label = "Inicio", date = "2024-01-15")
            Spacer(modifier = Modifier.height(8.dp))
            DateRow(label = "Fin", date = "2024-01-22")

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color(0xFFEEEEEE))
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Cambiar estado:",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusButton(
                    text = "Pendiente", 
                    isSelected = selectedStatus == "Pendiente", 
                    color = Color(0xFF6B7280),
                    onClick = { selectedStatus = "Pendiente" },
                    modifier = Modifier.weight(1f)
                )
                StatusButton(
                    text = "En progreso", 
                    isSelected = selectedStatus == "En progreso", 
                    color = Color(0xFF3B4BF6),
                    onClick = { selectedStatus = "En progreso" },
                    modifier = Modifier.weight(1f)
                )
                StatusButton(
                    text = "Terminado", 
                    isSelected = selectedStatus == "Terminado", 
                    color = Color(0xFF35904C),
                    onClick = { selectedStatus = "Terminado" },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun DateRow(label: String, date: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Text(text = date, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF37474F))
        }
    }
}

@Composable
fun StatusButton(
    text: String, 
    isSelected: Boolean, 
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) color else Color.White
    val textColor = if (isSelected) Color.White else color
    val borderColor = color

    Box(
        modifier = modifier
            .border(2.dp, borderColor, RoundedCornerShape(10.dp))
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}