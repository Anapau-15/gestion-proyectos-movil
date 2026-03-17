package mx.edu.utez.gestionproyectos.model

data class Task(
    val idTarea: Int,
    val nombre: String,
    val descripcion: String?,
    val prioridad: String,
    val estado: String,
    val fechaInicio: String,
    val fechaFin: String,
    val proyecto: Project
)