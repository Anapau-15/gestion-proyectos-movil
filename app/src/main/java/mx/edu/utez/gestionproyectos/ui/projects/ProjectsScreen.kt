package mx.edu.utez.gestionproyectos.ui.projects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.gestionproyectos.ui.components.ScreenWithLoader
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader
import mx.edu.utez.gestionproyectos.viewmodel.ProjectViewModel

@Composable
fun ProjectsScreen(
    viewModel: ProjectViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    val projects = viewModel.projects
    val isLoading = viewModel.isLoading // Variable corregida
    val errorMsg = viewModel.errorMsg

    val filteredProjects = projects
        .filter { projectWithProgress ->
            when (selectedTab) {
                0 -> true
                1 -> projectWithProgress.project.estado != "FINALIZADO"
                2 -> projectWithProgress.project.estado == "FINALIZADO"
                else -> true
            }
        }
        .filter { projectWithProgress ->
            projectWithProgress.project.nombre.contains(searchQuery, ignoreCase = true)
        }

    LaunchedEffect(Unit) {
        viewModel.loadProjects()
    }

    val tabs = listOf("Todos", "Activos", "Finalizados")

    // Usamos un Box para que el cargador aparezca ENCIMA del contenido
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
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
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar proyecto") },
                    leadingIcon = { Icon(Icons.Default.Search, "Buscar") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF1A4759),
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(0xFF1A4759)
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(text = title, color = Color(0xFF1A4759)) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Solo mostramos la lista o el mensaje de vacío si NO está cargando
                if (!isLoading) {
                    if (filteredProjects.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 60.dp),
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                        ) {
                            Text("No hay proyectos", fontSize = 18.sp, color = Color.Gray)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(filteredProjects) { item ->
                                ProjectCard(
                                    title = item.project.nombre,
                                    description = item.project.descripcion,
                                    progress = item.progress,
                                    id = item.project.idProyecto
                                )
                            }
                        }
                    }
                }
            }
        }

        // --- LA RUEDITA DE CARGA ---
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp), // Ajuste para que no tape el Header
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF3A7C78)
                )
            }
        }

        // --- MENSAJE DE ERROR ---
        errorMsg?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.align(androidx.compose.ui.Alignment.BottomCenter).padding(16.dp)
            )
        }
    }
}