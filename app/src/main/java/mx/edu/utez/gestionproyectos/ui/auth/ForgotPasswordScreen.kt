package mx.edu.utez.gestionproyectos.ui.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import mx.edu.utez.gestionproyectos.R
import mx.edu.utez.gestionproyectos.ui.components.AuthBackground
import mx.edu.utez.gestionproyectos.ui.components.AuthCard
import mx.edu.utez.gestionproyectos.ui.components.AuthLogo
import mx.edu.utez.gestionproyectos.ui.components.GradientButton

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    var email by remember { mutableStateOf("") }


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

            GradientButton(
                text = "Buscar Cuenta",
                onClick = onNext
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
}}