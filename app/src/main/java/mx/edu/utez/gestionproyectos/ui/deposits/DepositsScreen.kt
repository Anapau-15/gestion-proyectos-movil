import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.gestionproyectos.ui.deposits.DepositCard
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader
import mx.edu.utez.gestionproyectos.viewmodel.DepositViewModel

@Composable
fun DepositsScreen(
    viewModel: DepositViewModel = viewModel()
) {

    var selectedTab by remember { mutableStateOf(0) }

    val deposits = viewModel.deposits
    val isLoading = viewModel.isLoading.value

    LaunchedEffect(Unit) {
        viewModel.loadDeposits()
    }

    val filteredDeposits = when (selectedTab) {

        0 -> deposits.take(5)

        1 -> deposits

        else -> deposits
    }

    val total = deposits.sumOf { it.monto }

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
                text = "Saldo Actual: $${"%,.2f".format(total)}",
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
                        containerColor =
                            if (selectedTab == 0) Color(0xFF2F7E79)
                            else Color(0xFFE5E5E5)
                    )
                ) {
                    Text(
                        text = "Recientes",
                        color =
                            if (selectedTab == 0) Color.White
                            else Color.DarkGray
                    )
                }

                Button(
                    onClick = { selectedTab = 1 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (selectedTab == 1) Color(0xFF2F7E79)
                            else Color(0xFFE5E5E5)
                    )
                ) {
                    Text(
                        text = "Historial",
                        color =
                            if (selectedTab == 1) Color.White
                            else Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            when {

                isLoading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        CircularProgressIndicator()

                    }

                }

                deposits.isEmpty() -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "No hay depósitos registrados",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )

                    }

                }

                else -> {

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        items(filteredDeposits) { deposit ->

                            DepositCard(
                                title = deposit.proyecto?.nombre ?: "Proyecto",
                                type = deposit.concepto ?: "",
                                date = deposit.fechaPago ?: "",
                                amount = "$${deposit.monto}"
                            )

                        }
                    }

                        }
                    }

                }

            }

        }

