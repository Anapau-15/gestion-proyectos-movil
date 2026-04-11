package mx.edu.utez.gestionproyectos.ui.auth

import android.R.attr.onClick
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.gestionproyectos.ui.components.AuthBackground
import mx.edu.utez.gestionproyectos.ui.components.AuthCard
import mx.edu.utez.gestionproyectos.ui.components.AuthLogo
import mx.edu.utez.gestionproyectos.ui.components.GradientButton
import mx.edu.utez.gestionproyectos.viewmodel.LoginViewModel

@Composable
fun VerifyCodeScreen(
    onBack: () -> Unit,
    onNext: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {

    var token by remember { mutableStateOf("") }
    var yaAvanzó by remember { mutableStateOf(false) }

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

            AuthCard(title = "Verificar Código") {

                Text(
                    text = "Copia el código que recibiste en tu correo electrónico",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = token,
                    onValueChange = { token = it
                        viewModel.errorMessage = null },
                    label = { Text("Código de verificación") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                //  MOSTRAR ERRORES
                viewModel.errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                //  PASAR A SIGUIENTE PANTALLA SI VÁLIDO
                if (viewModel.tokenValido && !yaAvanzó) {
                    yaAvanzó = true
                    LaunchedEffect(Unit) {
                        onNext()
                    }
                }

                GradientButton(
                    text = if (viewModel.isLoading) "Verificando..." else "Verificar Código",
                    onClick = {
                        if (token.isNotEmpty()) {
                            viewModel.errorMessage = null  //  LIMPIAR ERROR ANTERIOR
                            viewModel.validateResetToken(token)
                        }
                    },
                    enabled = !viewModel.isLoading && token.isNotEmpty()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        token = ""
                        yaAvanzó = false
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