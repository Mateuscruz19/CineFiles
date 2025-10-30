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

import com.br.cinefiles.ui.components.ImagePlaceholder
import com.br.cinefiles.ui.components.ActionRow
import com.br.cinefiles.ui.components.CustomNavigationBar


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val DarkThemeColors = darkColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF212121),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.LightGray
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: String?,
    onVoltarClick: () -> Unit,
    viewModel: MovieDetailViewModel = viewModel()
) {
    // Coleta o estado da UI do ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                // Usa o título do filme quando carregado
                title = { Text(uiState.movie?.title ?: "Voltar") },
                navigationIcon = {
                    IconButton(onClick = onVoltarClick) {
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
            CustomNavigationBar()
        }
    ) { innerPadding ->

        // --- TRATAMENTO DE ESTADO ---
        when {
            // 1. Estado de Carregamento
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // 2. Estado de Erro
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

            // 3. Estado de Sucesso
            uiState.movie != null -> {
                // Variáveis de ajuda para formatar os dados
                val movie = uiState.movie!!

                // Converte a lista [GenreDto(name="Ação"), GenreDto(name="Ficção")]
                // para uma string "Ação, Ficção"
                val genres = movie.genres?.joinToString(", ") { it.name } ?: "Gênero não informado"

                // Converte a data "2019-10-17" para "2019"
                val year = movie.releaseDate?.let {
                    try {
                        LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE).year.toString()
                    } catch (e: Exception) { "Ano não informado" }
                } ?: "Ano não informado"

                // --- CONTEÚDO DA TELA ---
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // VSTACK | TITULO + GENERO
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = movie.title, // <-- DADO REAL
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = genres, // <-- DADO REAL
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // IMAGEM (Troquei o Placeholder pelo AsyncImage)
                    AsyncImage(
                        model = ApiConstants.IMAGE_BASE_URL + movie.backdropPath,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // DESCRICAO
                    Text(
                        text = movie.overview ?: "Sem descrição.", // <-- DADO REAL
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Justify
                    )

                    // VSTACK = DIRECAO + ANO
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            // "Direção" não vem nesta API.
                            // Precisaria de uma chamada extra para 'movie/{id}/credits'
                            text = "Direção: (Não disponível)",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "Ano: $year", // <-- DADO REAL
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    ActionRow()
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
