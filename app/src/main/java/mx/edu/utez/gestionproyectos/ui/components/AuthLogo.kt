package mx.edu.utez.gestionproyectos.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mx.edu.utez.gestionproyectos.R


@Composable
fun AuthLogo() {
    Image(
        painter = painterResource(id = R.drawable.logogs),
        contentDescription = "App Logo",
        modifier = Modifier.size(180.dp)
    )
}