package mx.edu.utez.gestionproyectos.ui.deposits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DepositCard(
    title: String,
    type: String,
    date: String,
    amount: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Column {

                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = type,
                        color = Color(0xFF2F7E79)
                    )

                    Text(
                        text = "Depósito: $date",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                AssistChip(
                    onClick = { },
                    label = { Text("Recibido") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFF2F7E79),
                        labelColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = amount,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1A4759)
            )
        }
    }
}