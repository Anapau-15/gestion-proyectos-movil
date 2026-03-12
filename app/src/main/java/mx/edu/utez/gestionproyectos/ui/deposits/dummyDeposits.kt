package mx.edu.utez.gestionproyectos.ui.deposits

data class Deposit(
    val project: String,
    val type: String,
    val date: String,
    val amount: String
)

val dummyDeposits = listOf(
    Deposit(
        "App Ecommerce",
        "Quincenal",
        "16 abril 2024",
        "$5,000.00"
    ),
    Deposit(
        "Control de registros",
        "Semanal",
        "12 abril 2024",
        "$3,200.00"
    ),
    Deposit(
        "Sistema de inventario",
        "Mensual",
        "8 abril 2024",
        "$8,500.00"
    )
)