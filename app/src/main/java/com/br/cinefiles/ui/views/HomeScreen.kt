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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlaylistAdd
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.br.cinefiles.ui.components.CustomNavigationBar
import com.br.cinefiles.ui.components.FeaturedMovie
import com.br.cinefiles.ui.components.MovieCard
import com.br.cinefiles.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel()
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
                // Header moved inside LazyColumn for better control
                item {
                    // Spacer to push content down from the top
                    Spacer(modifier = Modifier.height(48.dp))

                    // Row for Welcome Text and Profile Icon
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("O que vamos assistir hoje,", style = MaterialTheme.typography.titleLarge)
                            // TODO: Substituir "Usuário" pelo nome do usuário do banco de dados
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

                    // Row for "Filme da semana" title and action icons
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
                            IconButton(onClick = { /* TODO: Favorite action */ }) {
                                Icon(Icons.Default.FavoriteBorder, contentDescription = "Favoritos")
                            }
                            IconButton(onClick = { /* TODO: Add to list action */ }) {
                                Icon(Icons.Default.PlaylistAdd, contentDescription = "Adicionar à Lista")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Featured Movie
                item {
                    uiState.featuredMovie?.let { movie ->
                        FeaturedMovie(
                            movie = movie,
                            modifier = Modifier.clickable { navController.navigate("movieDetail/${movie.id}") }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Movie Categories
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
