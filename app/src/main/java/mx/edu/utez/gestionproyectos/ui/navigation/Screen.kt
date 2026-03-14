package mx.edu.utez.gestionproyectos.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector
) {

    object Home : Screen("home", "Inicio", Icons.Default.Home)
    object Projects : Screen("projects", "Proyectos", Icons.Default.Folder)
    object Deposits : Screen("deposits", "Depósitos", Icons.Default.Payments)
    object Tasks : Screen("tasks", "Tareas", Icons.Default.Assignment)
    object Profile : Screen("profile", "Perfil", Icons.Default.AccountCircle)
}