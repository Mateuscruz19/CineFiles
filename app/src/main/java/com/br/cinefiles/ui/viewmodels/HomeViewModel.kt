package com.br.cinefiles.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.cinefiles.BuildConfig
import com.br.cinefiles.data.api.ApiConstants
import com.br.cinefiles.data.api.TmdbApiClient
import com.br.cinefiles.data.models.MovieDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val featuredMovie: MovieDto? = null,
    val actionMovies: List<MovieDto> = emptyList(),
    val comedyMovies: List<MovieDto> = emptyList(),
    val horrorMovies: List<MovieDto> = emptyList(),
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    private val apiService = TmdbApiClient.apiService
    private val apiKey = BuildConfig.TMDB_API_KEY
    
    init {
        loadMovies()
    }
    
    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Load featured movie (popular movies)
                val popularResponse = apiService.getPopularMovies(apiKey)
                val featuredMovie = popularResponse.results.firstOrNull()
                
                // Load action movies
                val actionResponse = apiService.getMoviesByGenre(
                    apiKey = apiKey,
                    genreId = ApiConstants.GENRE_ACTION
                )
                
                // Load comedy movies
                val comedyResponse = apiService.getMoviesByGenre(
                    apiKey = apiKey,
                    genreId = ApiConstants.GENRE_COMEDY
                )
                
                // Load horror movies
                val horrorResponse = apiService.getMoviesByGenre(
                    apiKey = apiKey,
                    genreId = ApiConstants.GENRE_HORROR
                )
                
                _uiState.value = HomeUiState(
                    isLoading = false,
                    featuredMovie = featuredMovie,
                    actionMovies = actionResponse.results.take(10),
                    comedyMovies = comedyResponse.results.take(10),
                    horrorMovies = horrorResponse.results.take(10)
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Erro ao carregar filmes: ${e.message}"
                )
            }
        }
    }
}

