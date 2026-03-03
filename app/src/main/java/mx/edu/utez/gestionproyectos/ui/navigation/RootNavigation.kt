package mx.edu.utez.gestionproyectos.ui.navigation


import androidx.compose.runtime.*
import mx.edu.utez.gestionproyectos.ui.auth.LoginScreen

@Composable
fun RootNavigation() {

    var isLoggedIn by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        AppNavigation()
    } else {
        LoginScreen(
            onLoginClick = {
                isLoggedIn = true
            }
        )
    }
}