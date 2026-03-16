package mx.edu.utez.gestionproyectos.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {

    var showLogoutDialog by remember { mutableStateOf(false) }

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

            Surface(
                shape = CircleShape,
                color = Color(0xFF1A4759),
                modifier = Modifier.size(120.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Miguel Perez",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Lider del equipo",
                        color = Color(0xFF1A4759)
                    )

                    Text(
                        text = "miguelperez@gmail.com",
                        color = Color(0xFF1A4759)
                    )
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

                    Divider()

                   /*

                    ProfileOption(
                        icon = Icons.Default.Lock,
                        text = "Cambiar contraseña"
                    )
*/
                    ProfileOption(
                        icon = Icons.Default.Logout,
                        text = "Cerrar sesión",
                        color = Color.Red,
                        onClick = { // Pasamos el onClick de forma explícita
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
                onLogout() // Esto debe llamar a isLoggedIn = false en RootNavigation
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
            Text(
                text = "Cerrar Sesión",
                fontWeight = FontWeight.Bold
            )
        },

        text = {
            Text(
                text = "¿Estás seguro de cerrar sesión?"
            )
        },

        confirmButton = {

            Button(
                onClick = { onConfirm() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A4759)
                )
            ) {
                Text("Confirmar")
            }
        },

        dismissButton = {

            OutlinedButton(
                onClick = { onCancel() }
            ) {
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
            imageVector = Icons.Default.ChevronRight, // Cambiado KeyboardArrowDown por ChevronRight que es más común en menús
            contentDescription = null
        )
    }
}