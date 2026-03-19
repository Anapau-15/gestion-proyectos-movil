package mx.edu.utez.gestionproyectos.data.utils


import android.util.Base64
import org.json.JSONObject

fun decodeJWT(token: String): JSONObject {

    val parts = token.split(".")
    val payload = parts[1]

    val decoded = String(Base64.decode(payload, Base64.DEFAULT))

    return JSONObject(decoded)
}