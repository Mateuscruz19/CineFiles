package com.carla.cinefiles.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ActionRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            repeat(5) {
                Icon(
                    imageVector = Icons.Default.StarBorder,
                    contentDescription = "Estrela de avaliação",
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /* TODO: Ação de Favoritar */ }) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Favoritar",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        IconButton(onClick = { /* TODO: Ação de Adicionar */ }) {
            Icon(
                imageVector = Icons.Default.AddCircleOutline,
                contentDescription = "Adicionar",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}