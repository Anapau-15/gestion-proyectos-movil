package mx.edu.utez.gestionproyectos.ui.team

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mx.edu.utez.gestionproyectos.ui.home.HomeHeader

@Composable
fun TeamScreen() {

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
                text = "Equipo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Miembros",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )

                Surface(
                    shape = MaterialTheme.shapes.large,
                    color = Color(0xFFE5E5E5)
                ) {
                    Text(
                        text = teamMembers.size.toString(),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                LazyColumn {

                    items(teamMembers) { member ->

                        TeamMemberItem(member)

                        Divider()
                    }
                }
            }
        }
    }
}
@Composable
fun TeamMemberItem(member: TeamMember) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color(0xFF1A4759),
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            Text(
                text = member.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = member.role,
                color = Color.Gray
            )
        }
    }
}

data class TeamMember(
    val name: String,
    val role: String
)
val teamMembers = listOf(
    TeamMember("Monica Valdovinos", "Líder"),
    TeamMember("Ana Paula Gonzalez", "Desarrollador"),
    TeamMember("Naoly Rascón", "Desarrollador"),
    TeamMember("Yoselinne Mundo", "Tester"),
    TeamMember("Evelyn Barragan", "Tester")
)