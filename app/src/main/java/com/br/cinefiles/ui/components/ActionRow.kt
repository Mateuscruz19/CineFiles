package com.br.cinefiles.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlaylistAddCheck
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ActionRow(
    isFavorite: Boolean,
    isInMyList: Boolean,
    onFavoriteClick: () -> Unit,
    onMyListClick: () -> Unit
) {
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

        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favoritar",
                tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onBackground
            )
        }

        IconButton(onClick = onMyListClick) {
            Icon(
                imageVector = if (isInMyList) Icons.Default.PlaylistAddCheck else Icons.Default.AddCircleOutline,
                contentDescription = "Adicionar",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}