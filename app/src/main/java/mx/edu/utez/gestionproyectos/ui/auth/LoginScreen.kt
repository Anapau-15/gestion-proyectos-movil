package mx.edu.utez.gestionproyectos.ui.auth

import mx.edu.utez.gestionproyectos.ui.components.GradientButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import mx.edu.utez.gestionproyectos.R

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E6F6F),
                        Color(0xFF2E8B8B),
                        Color(0xFFDCEEEE)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.logogs),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(180.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¡Bienvenido!",
                fontSize = 28.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Control que impulsa proyectos",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Iniciar sesión",
                        fontSize = 22.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Usuario o correo") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, null)
                        },
                        trailingIcon = {
                            Icon(Icons.Default.Visibility, null)
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    GradientButton(
                        text = "Iniciar",
                        onClick = { onLoginClick() }
                    )
                }
            }
        }
    }
}