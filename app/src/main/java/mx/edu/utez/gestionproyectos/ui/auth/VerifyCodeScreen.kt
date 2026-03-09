package mx.edu.utez.gestionproyectos.ui.auth

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import mx.edu.utez.gestionproyectos.ui.components.AuthBackground
import mx.edu.utez.gestionproyectos.ui.components.AuthCard
import mx.edu.utez.gestionproyectos.ui.components.AuthLogo
import mx.edu.utez.gestionproyectos.ui.components.GradientButton

@Composable
fun VerifyCodeScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    var code by remember { mutableStateOf(List(6) { "" }) }

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

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(6) { index ->
                    OutlinedTextField(
                        value = code[index],
                        onValueChange = { value ->
                            if (value.length <= 1) {
                                code = code.toMutableList().also {
                                    it[index] = value
                                }
                            }
                        },
                        modifier = Modifier.width(48.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            GradientButton(
                text = "Verificar Código",
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