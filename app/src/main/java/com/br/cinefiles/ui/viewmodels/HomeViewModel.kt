package com.br.cinefiles.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.cinefiles.BuildConfig
import com.br.cinefiles.data.api.TmdbApiClient
import com.br.cinefiles.data.models.GenreDto
import com.br.cinefiles.data.models.MovieDto
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MovieCategory(
    val genre: GenreDto,
    val movies: List<MovieDto>
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val featuredMovie: MovieDto? = null,
    val movieCategories: List<MovieCategory> = emptyList(),
    val favoriteMovies: List<MovieDto> = emptyList(),
    val myListMovies: List<MovieDto> = emptyList(),
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

    fun toggleFavorite(movie: MovieDto) {
        viewModelScope.launch {
            val currentFavorites = _uiState.value.favoriteMovies.toMutableList()
            val isFavorite = currentFavorites.any { it.id == movie.id }

            if (isFavorite) {
                currentFavorites.removeAll { it.id == movie.id }
            } else {
                currentFavorites.add(0, movie)
            }
            _uiState.value = _uiState.value.copy(favoriteMovies = currentFavorites)
        }
    }

    fun toggleMyList(movie: MovieDto) {
        viewModelScope.launch {
            val currentMyList = _uiState.value.myListMovies.toMutableList()
            val isInList = currentMyList.any { it.id == movie.id }

            if (isInList) {
                currentMyList.removeAll { it.id == movie.id }
            } else {
                currentMyList.add(0, movie)
            }
            _uiState.value = _uiState.value.copy(myListMovies = currentMyList)
        }
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val popularResponseDeferred = async { apiService.getPopularMovies(apiKey) }

                val genresResponse = apiService.getGenres(apiKey)
                val genresToLoad = genresResponse.genres.take(5)

                val movieCategoriesDeferred = genresToLoad.map { genre ->
                    async {
                        val moviesResponse = apiService.getMoviesByGenre(apiKey, genre.id)
                        MovieCategory(genre, moviesResponse.results.take(10))
                    }
                }

                val featuredMovie = popularResponseDeferred.await().results.firstOrNull()
                val movieCategories = movieCategoriesDeferred.awaitAll()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    featuredMovie = featuredMovie,
                    movieCategories = movieCategories
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
