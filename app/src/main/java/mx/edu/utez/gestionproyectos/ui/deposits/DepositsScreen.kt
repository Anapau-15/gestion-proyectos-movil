package mx.edu.utez.gestionproyectos.ui.deposits

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader

@Composable
fun DepositsScreen() {

    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F4F4))
                .padding(20.dp)
        ) {

            Text(
                text = "Mis depósitos",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Saldo Actual: $25,000.00",
                fontSize = 20.sp,
                color = Color(0xFF1A4759),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = { selectedTab = 0 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == 0)
                            Color(0xFF2F7E79)
                        else
                            Color(0xFFE5E5E5)
                    )
                ) {
                    Text(
                        text = "Recientes",
                        color = if (selectedTab == 0) Color.White else Color.DarkGray
                    )
                }

                Button(
                    onClick = { selectedTab = 1 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == 1)
                            Color(0xFF2F7E79)
                        else
                            Color(0xFFE5E5E5)
                    )
                ) {
                    Text(
                        text = "Historial",
                        color = if (selectedTab == 1) Color.White else Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(dummyDeposits) { deposit ->

                    DepositCard(
                        title = deposit.project,
                        type = deposit.type,
                        date = deposit.date,
                        amount = deposit.amount
                    )
                }
            }
        }
    }
}