package mx.edu.utez.gestionproyectos.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavigation() {

    var isLoggedIn by remember { mutableStateOf(false) }
    val authNavController = rememberNavController()

    if (isLoggedIn) {
        AppNavigation(
            onLogout = {
                isLoggedIn = false
            }
        )
    } else {
        AuthNavigation(
            navController = authNavController,
            onLoginSuccess = {
                isLoggedIn = true
            }
        )
    }
}