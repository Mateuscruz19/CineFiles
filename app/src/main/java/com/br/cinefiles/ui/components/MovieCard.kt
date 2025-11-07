package com.br.cinefiles.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.br.cinefiles.data.api.ApiConstants
import com.br.cinefiles.data.models.MovieDto

@Composable
fun MovieCard(movie: MovieDto, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        AsyncImage(
            model = "${ApiConstants.IMAGE_BASE_URL}${movie.posterPath}",
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(150.dp)
        )
    }
}
