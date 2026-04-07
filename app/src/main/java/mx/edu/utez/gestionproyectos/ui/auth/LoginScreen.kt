package mx.edu.utez.gestionproyectos.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff // Importante para el ojo tachado
import androidx.compose.material3.*
import androidx.compose.runtime.* // Esto arregla el "mutableStateOf"
// 🔥 ESTAS DOS SON LAS QUE ARREGLAN EL ERROR DEL "by"
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.gestionproyectos.R
import mx.edu.utez.gestionproyectos.ui.components.AuthBackground
import mx.edu.utez.gestionproyectos.ui.components.AuthCard
import mx.edu.utez.gestionproyectos.ui.components.GradientButton
import mx.edu.utez.gestionproyectos.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 1. Agregamos el estado para la visibilidad
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            onLoginSuccess()
        }
    }

    AuthBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ... (Logo y textos de bienvenida se mantienen igual)
            Image(
                painter = painterResource(id = R.drawable.logogs),
                contentDescription = "Logo",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "¡Bienvenido!", fontSize = 28.sp, color = Color.White)

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Control que impulsa proyectos",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(28.dp))

            AuthCard(title = "Iniciar sesión") {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario o correo") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Modificamos el campo de Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },

                    // 3. Cambiamos el icono estático por uno interactivo
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                // Cambiamos el icono según el estado booleano
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },

                    // 4. Cambiamos la transformación según el estado
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),

                    modifier = Modifier.fillMaxWidth()
                )

                // ... (El resto del código: Botón, Error y Loader se mantienen igual)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clickable { onForgotPasswordClick() }
                        .padding(vertical = 6.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                GradientButton(
                    text = "Iniciar",
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty()) {
                            viewModel.login(username, password)
                        }
                    }
                )

                if (viewModel.errorMessage != null) {
                    Text(
                        text = viewModel.errorMessage!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp).padding(top = 8.dp),
                        color = Color(0xFF3A7C78)
                    )
                }
            }
        }
    }
}