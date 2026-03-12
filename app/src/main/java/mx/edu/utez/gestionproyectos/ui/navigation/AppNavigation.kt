package mx.edu.utez.gestionproyectos.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import mx.edu.utez.gestionproyectos.ui.home.HomeScreen
import mx.edu.utez.gestionproyectos.ui.projects.ProjectsScreen
import mx.edu.utez.gestionproyectos.ui.deposits.DepositsScreen
import mx.edu.utez.gestionproyectos.ui.team.TeamScreen
import mx.edu.utez.gestionproyectos.ui.profile.ProfileScreen

@Composable
fun AppNavigation(
    onLogout: () -> Unit
) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {

            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp
            ) {

                val items = listOf(
                    Screen.Home,
                    Screen.Projects,
                    Screen.Deposits,
                    Screen.Team,
                    Screen.Profile
                )

                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                items.forEach { screen ->

                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.label,
                                tint = if (currentRoute == screen.route)
                                    Color(0xFF247273)
                                else
                                    Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = screen.label,
                                color = if (currentRoute == screen.route)
                                    Color(0xFF247273)
                                else
                                    Color.Gray
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Projects.route) { ProjectsScreen() }
            composable(Screen.Deposits.route) { DepositsScreen() }
            composable(Screen.Team.route) { TeamScreen() }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onLogout = onLogout
                )

            }
        }
    }
}