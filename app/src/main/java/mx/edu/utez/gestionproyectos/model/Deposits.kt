package mx.edu.utez.gestionproyectos.model


data class Deposit(
    val idPago: Int,
    val concepto: String,
    val descripcion: String,
    val fechaPago: String,
    val monto: Double,
    val proyecto: Proyecto
)

data class Proyecto(
    val nombre: String
)