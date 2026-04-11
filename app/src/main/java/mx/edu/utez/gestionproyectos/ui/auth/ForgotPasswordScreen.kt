package mx.edu.utez.gestionproyectos.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.gestionproyectos.ui.components.AuthBackground
import mx.edu.utez.gestionproyectos.ui.components.AuthCard
import mx.edu.utez.gestionproyectos.ui.components.AuthLogo
import mx.edu.utez.gestionproyectos.ui.components.GradientButton
import mx.edu.utez.gestionproyectos.viewmodel.LoginViewModel

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    onNext: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {

    var email by remember { mutableStateOf("") }


        if (viewModel.forgotPasswordSuccess != null) {
            // Extraer token del mensaje "Token: xxxxx"
            val mensaje = viewModel.forgotPasswordSuccess ?: ""
            val token = mensaje.substringAfter("Token: ").trim()

            println("🔑 TOKEN EXTRAÍDO: $token")

            if (token.isNotEmpty()) {
                viewModel.resetToken = token
                println("✅ Token guardado en viewModel: ${viewModel.resetToken}")
            }

            LaunchedEffect(Unit) {
                onNext()
            }

    }

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
                    text = "Ingresa tu correo electrónico para buscar tu cuenta y enviarte un código de verificación.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 🔥 MOSTRAR ERRORES
                viewModel.forgotPasswordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (viewModel.forgotPasswordSuccess != null) {
                    // Extraer token del mensaje (formato: "Se envió... Token: xxxxx")
                    val parts = viewModel.forgotPasswordSuccess!!.split("Token: ")
                    if (parts.size > 1) {
                        viewModel.resetToken = parts[1]
                    }
                    LaunchedEffect(Unit) {
                        onNext()
                    }
                }

                GradientButton(
                    text = if (viewModel.forgotPasswordLoading) "Enviando..." else "Buscar Cuenta",
                    onClick = {
                        if (email.isNotEmpty()) {
                            println("DEBUG: Enviando email: $email")
                            viewModel.forgotPassword(email)

                            println("DEBUG: forgotPasswordSuccess = ${viewModel.forgotPasswordSuccess}")
                            println("DEBUG: forgotPasswordError = ${viewModel.forgotPasswordError}")
                        }
                    },
                    enabled = !viewModel.forgotPasswordLoading && email.isNotEmpty()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}