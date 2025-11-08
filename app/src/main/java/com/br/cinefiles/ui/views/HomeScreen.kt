package com.br.cinefiles.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.PlaylistAddCheck
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.br.cinefiles.ui.components.CustomNavigationBar
import com.br.cinefiles.ui.components.FeaturedMovie
import com.br.cinefiles.ui.components.MovieCard
import com.br.cinefiles.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = { CustomNavigationBar(navController = navController) }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                item {
                    Spacer(modifier = Modifier.height(48.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("O que vamos assistir hoje,", style = MaterialTheme.typography.titleLarge)
                            Text("Usuário?", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        }
                        IconButton(onClick = { navController.navigate("profile") }) {
                            Icon(
                                Icons.Outlined.Person,
                                contentDescription = "Perfil",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Filme da semana",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row {
                            val isFavorite = uiState.favoriteMovies.any { it.id == uiState.featuredMovie?.id }
                            val isInMyList = uiState.myListMovies.any { it.id == uiState.featuredMovie?.id }

                            IconButton(onClick = { uiState.featuredMovie?.let { viewModel.toggleFavorite(it) } }) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favoritos",
                                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onBackground
                                )
                            }
                            IconButton(onClick = { uiState.featuredMovie?.let { viewModel.toggleMyList(it) } }) {
                                Icon(
                                    imageVector = if (isInMyList) Icons.Default.PlaylistAddCheck else Icons.Default.PlaylistAdd,
                                    contentDescription = "Adicionar à Lista"
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    uiState.featuredMovie?.let {
                        FeaturedMovie(
                            movie = it,
                            modifier = Modifier.clickable { navController.navigate("movieDetail/${it.id}") }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (uiState.favoriteMovies.isNotEmpty()) {
                    item {
                        Text(
                            text = "Favoritos",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(uiState.favoriteMovies) { movie ->
                                MovieCard(
                                    movie = movie,
                                    modifier = Modifier.clickable { navController.navigate("movieDetail/${movie.id}") }
                                )
                            }
                        }
                    }
                }

                if (uiState.myListMovies.isNotEmpty()) {
                    item {
                        Text(
                            text = "Minha Lista",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(uiState.myListMovies) { movie ->
                                MovieCard(
                                    movie = movie,
                                    modifier = Modifier.clickable { navController.navigate("movieDetail/${movie.id}") }
                                )
                            }
                        }
                    }
                }

                items(uiState.movieCategories) { category ->
                    Text(
                        text = category.genre.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(category.movies) { movie ->
                            MovieCard(
                                movie = movie,
                                modifier = Modifier.clickable { navController.navigate("movieDetail/${movie.id}") }
                            )
                        }
                    }
                }
            }
        }
    }
}
