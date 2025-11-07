package com.br.cinefiles.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.br.cinefiles.data.api.ApiConstants
import com.br.cinefiles.data.models.MovieDto

@Composable
fun MovieCard(movie: MovieDto, modifier: Modifier = Modifier) {
    Card(modifier = modifier.width(150.dp)) {
        Column {
            AsyncImage(
                model = "${ApiConstants.IMAGE_BASE_URL}${movie.posterPath}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface) // Cor de fundo do app
            ) {
                Text(
                    text = movie.title,
                    color = MaterialTheme.colorScheme.onSurface, // Cor do texto para o fundo do app
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
