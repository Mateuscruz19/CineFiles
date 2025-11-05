package com.br.cinefiles.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.br.cinefiles.data.api.ApiConstants
import com.br.cinefiles.ui.components.CustomNavigationBar
import com.br.cinefiles.ui.components.SearchResultItem
import com.br.cinefiles.ui.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = { CustomNavigationBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                value = uiState.query,
                onValueChange = { viewModel.onQueryChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Pesquisar...", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Executar busca",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            viewModel.onQueryChange(uiState.query)
                        }
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    uiState.error != null -> {
                        Text(
                            text = uiState.error!!,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.searchResults) { movie ->
                                val genreMap = mapOf(
                                    ApiConstants.GENRE_ACTION to "Ação",
                                    ApiConstants.GENRE_ADVENTURE to "Aventura",
                                    ApiConstants.GENRE_ANIMATION to "Animação",
                                    ApiConstants.GENRE_COMEDY to "Comédia",
                                    ApiConstants.GENRE_CRIME to "Crime",
                                    ApiConstants.GENRE_DOCUMENTARY to "Documentário",
                                    ApiConstants.GENRE_DRAMA to "Drama",
                                    ApiConstants.GENRE_FAMILY to "Família",
                                    ApiConstants.GENRE_FANTASY to "Fantasia",
                                    ApiConstants.GENRE_HISTORY to "História",
                                    ApiConstants.GENRE_HORROR to "Terror",
                                    ApiConstants.GENRE_MUSIC to "Música",
                                    ApiConstants.GENRE_MYSTERY to "Mistério",
                                    ApiConstants.GENRE_ROMANCE to "Romance",
                                    ApiConstants.GENRE_SCIENCE_FICTION to "Ficção Científica",
                                    ApiConstants.GENRE_TV_MOVIE to "Filme de TV",
                                    ApiConstants.GENRE_THRILLER to "Suspense",
                                    ApiConstants.GENRE_WAR to "Guerra",
                                    ApiConstants.GENRE_WESTERN to "Faroeste"
                                )
                                val genresText = movie.genreIds
                                    ?.mapNotNull { genreMap[it] }
                                    ?.joinToString(", ")

                                SearchResultItem(
                                    title = movie.title,
                                    thumbnailUrl = ApiConstants.IMAGE_BASE_URL + movie.posterPath,
                                    genre = if (genresText.isNullOrEmpty()) "Gênero não informado" else genresText,
                                    modifier = Modifier.clickable {
                                        navController.navigate("movieDetail/${movie.id}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    val fakeNavController = rememberNavController()

    MaterialTheme(colorScheme = darkColorScheme()) {
        SearchScreen(navController = fakeNavController)
    }
}
