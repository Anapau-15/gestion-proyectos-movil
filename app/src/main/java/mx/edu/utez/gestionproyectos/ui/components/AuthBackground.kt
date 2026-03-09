package mx.edu.utez.gestionproyectos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AuthBackground(content: @Composable () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF1A4659),
                        Color(0xFF247273),
                        Color(0xFFDCEEEE)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}