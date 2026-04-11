package mx.edu.utez.gestionproyectos.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.gestionproyectos.ui.components.AuthBackground
import mx.edu.utez.gestionproyectos.ui.components.AuthCard
import mx.edu.utez.gestionproyectos.ui.components.AuthLogo
import mx.edu.utez.gestionproyectos.ui.components.GradientButton
import mx.edu.utez.gestionproyectos.viewmodel.LoginViewModel

@Composable
fun ResetPasswordScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // 🔥 NUEVO: ESTADOS PARA MOSTRAR/OCULTAR CONTRASEÑA
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    AuthBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AuthLogo()
            Spacer(modifier = Modifier.height(24.dp))

            AuthCard(title = "Restablecer Contraseña") {

                Text(
                    text = "Requisitos: Mínimo 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial (@#\$%&*!)",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 🔥 NUEVO: CON OJO PARA VER/OCULTAR
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Nueva Contraseña") },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔥 NUEVO: CON OJO PARA VER/OCULTAR
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar Contraseña") },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (confirmPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 🔥 ERRORES DE VALIDACIÓN LOCAL
                passwordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // 🔥 ERRORES DEL SERVIDOR
                viewModel.errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // 🔥 ÉXITO Y VOLVER AL LOGIN
                if (viewModel.resetPasswordSuccess != null) {
                    LaunchedEffect(Unit) {
                        onFinish()
                    }
                }

                GradientButton(
                    text = if (viewModel.isLoading) "Guardando..." else "Guardar Contraseña",
                    onClick = {
                        passwordError = null

                        when {
                            password.isEmpty() || confirmPassword.isEmpty() -> {
                                passwordError = "Por favor, llena todos los campos"
                            }
                            password != confirmPassword -> {
                                passwordError = "Las contraseñas no coinciden"
                            }
                            else -> {
                                viewModel.resetPassword(password)
                            }
                        }
                    },
                    enabled = !viewModel.isLoading && password.isNotEmpty() && confirmPassword.isNotEmpty()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        password = ""
                        confirmPassword = ""
                        passwordError = null
                        viewModel.clearMessages()
                        viewModel.tokenValido = false
                        viewModel.resetToken = null
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}