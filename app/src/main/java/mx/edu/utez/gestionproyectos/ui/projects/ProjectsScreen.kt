package mx.edu.utez.gestionproyectos.ui.projects

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader

@Composable
fun ProjectsScreen() {

    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf("Todos", "Activos", "Finalizados")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "Proyectos",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Buscar proyecto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            TabRow(
                selectedTabIndex = selectedTab
            ) {

                tabs.forEachIndexed { index, title ->

                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(dummyProjects) { project ->

                    ProjectCard(
                        title = project.name,
                        progress = project.progress
                    )
                }
            }
        }
    }
}

data class Project(
    val name: String,
    val progress: Float
)

val dummyProjects = listOf(
    Project("App Ecommerce", 0.15f),
    Project("Control Institucional", 0.50f),
    Project("Gestión Materiales", 0.93f)
)