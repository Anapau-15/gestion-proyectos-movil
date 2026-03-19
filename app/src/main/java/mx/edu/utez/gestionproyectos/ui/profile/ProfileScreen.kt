package mx.edu.utez.gestionproyectos.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.gestionproyectos.data.RetrofitClient
import mx.edu.utez.gestionproyectos.data.SessionManager
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {

    var showLogoutDialog by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("Cargando...") }
    var correo by remember { mutableStateOf("Cargando...") }
    var rol by remember { mutableStateOf("Cargando...") }
    var isLoading by remember { mutableStateOf(true) }

    // 🔥 LLAMADA AL BACKEND
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.apiService.getUser(
                "Bearer ${SessionManager.token}"

            )
            println("TOKEN ENVIADO: Bearer ${SessionManager.token}")

            response.data?.let {
                nombre = it.nombre
                correo = it.correo
                rol = it.rol
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    val rolFormateado = rol.lowercase().replaceFirstChar { it.uppercase() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F4F4))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Perfil",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 Avatar con inicial
            Surface(
                shape = CircleShape,
                color = Color(0xFF1A4759),
                modifier = Modifier.size(120.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = nombre.firstOrNull()?.uppercase() ?: "U",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 CARD CON LOADING
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {

                    if (isLoading) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFF1A4759)
                            )

                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = nombre,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = rolFormateado,
                                color = Color(0xFF3A7C78)
                            )

                            Text(
                                text = correo,
                                color = Color(0xFF1A4759)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Configuración",
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider()

                    ProfileOption(
                        icon = Icons.Default.Logout,
                        text = "Cerrar sesión",
                        color = Color.Red,
                        onClick = {
                            showLogoutDialog = true
                        }
                    )
                }
            }
        }
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onCancel = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false

                // 🔥 limpiar sesión
                SessionManager.token = ""
                SessionManager.nombre = ""
                SessionManager.rol = ""
                SessionManager.correo = ""

                onLogout()
            }
        )
    }
}

@Composable
fun LogoutDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            Text("Cerrar Sesión", fontWeight = FontWeight.Bold)
        },
        text = {
            Text("¿Estás seguro de cerrar sesión?")
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A4759)
                )
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun ProfileOption(
    icon: ImageVector,
    text: String,
    color: Color = Color.DarkGray,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            color = color,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null
        )
    }
}