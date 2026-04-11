package mx.edu.utez.gestionproyectos.ui.home

import HomeMenuCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.utez.gestionproyectos.data.SessionManager
import mx.edu.utez.gestionproyectos.ui.navigation.Screen
import mx.edu.utez.gestionproyectos.ui.projects.ProjectCard
import mx.edu.utez.gestionproyectos.viewmodel.ProjectViewModel

@Composable
fun HomeScreen(navController: NavController) {

    val projectViewModel: ProjectViewModel = viewModel()

    LaunchedEffect(Unit) {
        projectViewModel.loadProjects()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HomeHeader()

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "Inicio",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = SessionManager.nombre,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (projectViewModel.isLoading) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF3A7C78)
                    )
                }

            } else {

                projectViewModel.projects.firstOrNull()?.let { projectWithProgress ->

                    ProjectCard(
                        title = projectWithProgress.project.nombre,
                        progress = projectWithProgress.progress / 100f,
                        id = projectWithProgress.project.idProyecto,
                        description = projectWithProgress.project.descripcion
                    )

                } ?: Text("No tienes proyectos")
            }

            Spacer(modifier = Modifier.height(20.dp))

            HomeMenuCard(
                icon = Icons.Default.Assignment,
                text = "Mis Tareas",
                onClick = { navController.navigate(Screen.Tasks.route) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeMenuCard(
                icon = Icons.Default.Payments,
                text = "Mis depósitos",
                onClick = { navController.navigate(Screen.Deposits.route) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeMenuCard(
                icon = Icons.Default.Folder,
                text = "Mis proyectos",
                onClick = { navController.navigate(Screen.Projects.route) }
            )
        }
    }
}