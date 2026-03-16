package mx.edu.utez.gestionproyectos.data


data class ApiResponse<T>(

    val data: T?,
    val error: Boolean,
    val message: String?,
    val status: String

)