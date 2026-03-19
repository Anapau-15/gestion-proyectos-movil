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
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import mx.edu.utez.gestionproyectos.data.RetrofitClient
import mx.edu.utez.gestionproyectos.data.SessionManager
import mx.edu.utez.gestionproyectos.model.Project
import mx.edu.utez.gestionproyectos.ui.navigation.Screen
import mx.edu.utez.gestionproyectos.ui.projects.ProjectCard

@Composable
fun HomeScreen(navController: NavController) {

    var proyecto by remember { mutableStateOf<Project?>(null) }
    var loading by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    // 🔥 LLAMADA AL BACKEND
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getProjects(
                    "Bearer ${SessionManager.token}"
                )

                proyecto = response.data?.firstOrNull()
                loading = false

            } catch (e: Exception) {
                e.printStackTrace()
                loading = false
            }
        }
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

            // 🔥 PROYECTO DINÁMICO
            if (loading) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF3A7C78)
                    )
                }

            } else {

                proyecto?.let { p ->

                    ProjectCard(
                        title = p.nombre,
                        progress = p.progreso / 100f,
                        id = p.idProyecto,
                        description = p.descripcion
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