package com.br.cinefiles.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.cinefiles.BuildConfig
import com.br.cinefiles.data.api.TmdbApiClient
import com.br.cinefiles.data.models.MovieDetailDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estado da UI para a tela de detalhes.
 */
data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movie: MovieDetailDto? = null,
    val error: String? = null
)

class MovieDetailViewModel(
    savedStateHandle: SavedStateHandle // Injetado automaticamente pelo Compose Navigation
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    private val apiService = TmdbApiClient.apiService
    private val apiKey = BuildConfig.TMDB_API_KEY

    // Pega o "movieId" que foi passado na rota de navegação
    private val movieId: String = checkNotNull(savedStateHandle["movieId"])

    init {
        loadMovieDetails()
    }

    fun loadMovieDetails() {
        // Garante que o ID não está nulo ou vazio
        if (movieId.isBlank()) {
            _uiState.value = MovieDetailUiState(error = "ID do filme inválido.")
            return
        }

        viewModelScope.launch {
            _uiState.value = MovieDetailUiState(isLoading = true, error = null)

            try {
                // Chama a *nova* função da API
                val movieDetails = apiService.getMovieDetails(
                    apiKey = apiKey,
                    movieId = movieId.toInt() // Converte o ID de String para Int
                )

                _uiState.value = MovieDetailUiState(
                    isLoading = false,
                    movie = movieDetails
                )

            } catch (e: Exception) {
                _uiState.value = MovieDetailUiState(
                    isLoading = false,
                    error = "Erro ao carregar detalhes: ${e.message}"
                )
            }
        }
    }
}