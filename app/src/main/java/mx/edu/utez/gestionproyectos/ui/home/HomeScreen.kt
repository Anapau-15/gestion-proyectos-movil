package mx.edu.utez.gestionproyectos.ui.home

import HomeMenuCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import mx.edu.utez.gestionproyectos.ui.navigation.Screen
import mx.edu.utez.gestionproyectos.ui.projects.ProjectCard

@Composable
fun HomeScreen(navController: NavController) {

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
                text = "Evelyn B",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProjectCard(
                title = "App Ecomerce",
                progress = 0.15f,
                id = 1,
                description = "Hola"
            )

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
                onClick = {navController.navigate(Screen.Deposits.route)}

            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeMenuCard(
                icon = Icons.Default.Folder,
                text = "Mis proyectos",
                onClick = {navController.navigate(Screen.Projects.route)}

            )
        }
    }
}