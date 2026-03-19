package mx.edu.utez.gestionproyectos.model

import com.google.gson.annotations.SerializedName

data class Project(

    @SerializedName("idProyecto")
    val idProyecto: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("estado")
    val estado: String,

    @SerializedName("progreso")
    val progreso: Int

)