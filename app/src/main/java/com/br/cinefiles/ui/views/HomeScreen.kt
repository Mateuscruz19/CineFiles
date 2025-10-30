package com.br.cinefiles.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.br.cinefiles.data.api.ApiConstants
import com.br.cinefiles.data.models.MovieDto
import com.br.cinefiles.ui.theme.CinefilesTheme
import com.br.cinefiles.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    
    CinefilesTheme(darkTheme = true) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        TopBarSection()
                    }
                    
                    uiState.featuredMovie?.let { movie ->
                        item {
                            FeaturedMovieSection(
                                movie = movie,
                                snackbarHostState = snackbarHostState,
                                scope = scope,
                                onMovieClick = { movieId ->
                                    navController.navigate("movieDetail/$movieId")
                                }
                            )
                        }
                    }

                    if (uiState.actionMovies.isNotEmpty()) {
                        item {
                            MovieCategorySection(
                                categoryTitle = "Ação",
                                movieList = uiState.actionMovies,
                                onMovieClick = { movieId ->
                                    navController.navigate("movieDetail/$movieId")
                                }
                            )
                        }
                    }
                    
                    if (uiState.comedyMovies.isNotEmpty()) {
                        item {
                            MovieCategorySection(
                                categoryTitle = "Comédia",
                                movieList = uiState.comedyMovies,
                                onMovieClick = { movieId ->
                                    navController.navigate("movieDetail/$movieId")
                                }
                            )
                        }
                    }
                    
                    if (uiState.horrorMovies.isNotEmpty()) {
                        item {
                            MovieCategorySection(
                                categoryTitle = "Terror",
                                movieList = uiState.horrorMovies,
                                onMovieClick = { movieId ->
                                    navController.navigate("movieDetail/$movieId")
                                }
                            )
                        }
                    }
                    
                    item {
                        CopyrightFooter()
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCategorySection(
    categoryTitle: String,
    movieList: List<MovieDto>,
    onMovieClick: (String) -> Unit) {
    Column {
        Text(
            text = categoryTitle,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(movieList) { movie ->
                MoviePosterCard(movie = movie, onMovieClick = onMovieClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviePosterCard(
    movie: MovieDto,
    onMovieClick: (String) -> Unit
) {
    Card(
        onClick = { //TODO: ir para a tela de detalhes do filme
            onMovieClick(movie.id.toString())
        },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1C)
        )
    ) {
        Column(
            modifier = Modifier.width(140.dp)
        ) {
            AsyncImage(
                model = ApiConstants.IMAGE_BASE_URL + movie.posterPath,
                contentDescription = "Pôster do filme ${movie.title}",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 2
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedMovieSection(
    movie: MovieDto,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onMovieClick: (String) -> Unit
) {
    var isFavorited by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filme da Semana",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon( //TODO: fazer saporra favoritar de verdade
                    imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favoritar",
                    tint = if (isFavorited) Color.Red else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable {
                        isFavorited = !isFavorited
                        if (isFavorited) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Adicionado aos favoritos!")
                            }
                        }
                    }
                )
                Icon( //TODO: fazer saporra adicionar de verdade
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar à lista",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable {
                        scope.launch {
                            snackbarHostState.showSnackbar("Adicionado à sua lista!")
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            onClick = { //TODO: ir para a tela de detalhes do filme
                onMovieClick(movie.id.toString())
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column {
                AsyncImage(
                    model = ApiConstants.IMAGE_BASE_URL + movie.backdropPath,
                    contentDescription = "Poster do filme ${movie.title}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = movie.title,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun TopBarSection() {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "O que vamos assistir hoje,",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Usuário?", //TODO: mudar para o nome do usuário do login
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Ícone do Perfil",
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun CopyrightFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "© 2025 André Oids. Todos os direitos reservados.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    CinefilesTheme(darkTheme = true) {
//        HomeScreen()
//    }
//}
