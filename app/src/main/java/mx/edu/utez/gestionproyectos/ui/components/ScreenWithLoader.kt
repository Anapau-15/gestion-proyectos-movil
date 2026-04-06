package mx.edu.utez.gestionproyectos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScreenWithLoader(
    loading: Boolean,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {

    if (loading) {

        // 🔥 LOADER GLOBAL
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF3A7C78)
            )
        }

    } else {

        // 🔥 SCROLL GLOBAL
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            content()
        }
    }
}