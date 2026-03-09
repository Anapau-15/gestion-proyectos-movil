package mx.edu.utez.gestionproyectos.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mx.edu.utez.gestionproyectos.R

@Composable
fun HomeHeader() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF1F4E5F),
                        Color(0xFF2F8C85)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.logogs),
                contentDescription = "Logo",
                modifier = Modifier.height(60.dp)
            )

            Text(
                text = "Gestión de proyectos",
                color = Color.White
            )
        }
    }
}