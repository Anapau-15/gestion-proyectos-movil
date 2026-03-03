package mx.edu.utez.gestionproyectos.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector
) {

    object Home : Screen("home", "Inicio", Icons.Default.Home)
    object Projects : Screen("projects", "Proyectos", Icons.Default.List)
    object Deposits : Screen("deposits", "Depósitos", Icons.Default.Payments)
    object Team : Screen("team", "Equipos", Icons.Default.People)
    object Profile : Screen("profile", "Perfil", Icons.Default.AccountCircle)
}