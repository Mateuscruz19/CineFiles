package com.br.cinefiles.ui.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil.compose.AsyncImage
import com.br.cinefiles.data.api.ApiConstants
import com.br.cinefiles.ui.viewmodels.MovieDetailViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.br.cinefiles.ui.components.ActionRow
import com.br.cinefiles.ui.components.CustomNavigationBar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.br.cinefiles.data.models.toMovieDto
import com.br.cinefiles.ui.viewmodels.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: String?,
    navController: NavController,
    viewModel: MovieDetailViewModel = viewModel(),
    homeViewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val homeUiState by homeViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.movie?.title ?: "Detalhes do Filme") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        }
    ) { innerPadding ->

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }

            uiState.movie != null -> {
                val movie = uiState.movie!!
                val genres = movie.genres?.joinToString(", ") { it.name } ?: "Gênero não informado"
                val year = movie.releaseDate?.let {
                    try {
                        LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE).year.toString()
                    } catch (e: Exception) { "Ano não informado" }
                } ?: "Ano não informado"

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = genres,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    AsyncImage(
                        model = ApiConstants.IMAGE_BASE_URL + movie.backdropPath,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = movie.overview ?: "Sem descrição.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Justify
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Direção: (Não disponível)",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "Ano: $year",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    val isFavorite = homeUiState.favoriteMovies.any { it.id == movie.id }
                    val isInMyList = homeUiState.myListMovies.any { it.id == movie.id }

                    ActionRow(
                        isFavorite = isFavorite,
                        isInMyList = isInMyList,
                        onFavoriteClick = { homeViewModel.toggleFavorite(movie.toMovieDto()) },
                        onMyListClick = { homeViewModel.toggleMyList(movie.toMovieDto()) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
