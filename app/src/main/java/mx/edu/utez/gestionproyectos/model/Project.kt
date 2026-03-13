package mx.edu.utez.gestionproyectos.model


data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val status: String
)