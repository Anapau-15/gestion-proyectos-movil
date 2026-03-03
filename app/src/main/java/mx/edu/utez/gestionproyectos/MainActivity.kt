package mx.edu.utez.gestionproyectos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.edu.utez.gestionproyectos.ui.navigation.AppNavigation
import mx.edu.utez.gestionproyectos.ui.theme.GestionProyectosTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GestionProyectosTheme {
                AppNavigation()
            }
        }
    }
}