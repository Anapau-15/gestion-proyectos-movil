package mx.edu.utez.gestionproyectos.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavigation() {
    val rootNavController = rememberNavController()
    
    // Usamos un NavHost para controlar si estamos en el flujo de Auth o en el de la App
    NavHost(
        navController = rootNavController,
        startDestination = "auth_flow"
    ) {
        // Flujo de Autenticación
        composable("auth_flow") {
            val authNavController = rememberNavController()
            AuthNavigation(
                navController = authNavController,
                onLoginSuccess = {
                    // Al iniciar sesión, vamos a la app y borramos el historial de auth
                    rootNavController.navigate("app_flow") {
                        popUpTo("auth_flow") { inclusive = true }
                    }
                }
            )
        }

        // Flujo Principal de la Aplicación
        composable("app_flow") {
            AppNavigation(
                onLogout = {
                    // Al cerrar sesión, volvemos al login y borramos el historial de la app
                    rootNavController.navigate("auth_flow") {
                        popUpTo("app_flow") { inclusive = true }
                    }
                }
            )
        }
    }
}