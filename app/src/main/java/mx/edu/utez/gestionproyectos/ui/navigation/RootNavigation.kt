package mx.edu.utez.gestionproyectos.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavigation() {

    var isLoggedIn by remember { mutableStateOf(false) }

    if (isLoggedIn) {

        AppNavigation()

    } else {

        val navController = rememberNavController()

        AuthNavigation(
            navController = navController,
            onLoginSuccess = {
                isLoggedIn = true
            }
        )
    }
}