package mx.edu.utez.gestionproyectos.ui.auth

import android.R.attr.password
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.gestionproyectos.R
import mx.edu.utez.gestionproyectos.ui.components.AuthBackground
import mx.edu.utez.gestionproyectos.ui.components.AuthCard
import mx.edu.utez.gestionproyectos.ui.components.GradientButton

@Composable

fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
)
{
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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

            Image(
                painter = painterResource(id = R.drawable.logogs),
                contentDescription = "Logo",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "¡Bienvenido!",
                fontSize = 28.sp,
                color = Color(0xFFFFFFFF)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Control que impulsa proyectos",
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF).copy(alpha = 0.9f)
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

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.Visibility, contentDescription = null) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

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
                        } else {
                            // Aquí podrías mostrar un mensaje de "Llena los campos"
                            println("Campos vacíos")
                        }
                    }
                )

                // Dentro de AuthCard en LoginScreen.kt
                if (viewModel.errorMessage != null) {
                    Text(
                        text = viewModel.errorMessage!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                if (viewModel.isLoading) {
                    Text("Cargando...", color = Color.White) // O un CircularProgressIndicator
                }
            }
        }
    }
}