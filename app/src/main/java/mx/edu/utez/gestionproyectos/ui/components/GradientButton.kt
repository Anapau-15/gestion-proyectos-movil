package mx.edu.utez.gestionproyectos.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF1A4659),
            Color(0xFF247273)
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(30.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}