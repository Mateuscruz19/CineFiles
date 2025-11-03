package com.br.cinefiles.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.cinefiles.BuildConfig
import com.br.cinefiles.data.api.TmdbApiClient
import com.br.cinefiles.data.models.MovieDto
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val isLoading: Boolean = false,
    val searchResults: List<MovieDto> = emptyList(),
    val error: String? = null,
    val query: String = ""
)

class SearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val apiService = TmdbApiClient.apiService
    private val apiKey = BuildConfig.TMDB_API_KEY
    private var searchJob: Job? = null

    fun onQueryChange(newQuery: String) {
        _uiState.value = _uiState.value.copy(query = newQuery)
        searchJob?.cancel()

        if (newQuery.isNotBlank()) {
            searchJob = viewModelScope.launch {
                delay(500)
                searchMovies(newQuery)
            }
        } else {
            _uiState.value = _uiState.value.copy(searchResults = emptyList(), error = null)
        }
    }

    private suspend fun searchMovies(query: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        try {
            val response = apiService.searchMovies(apiKey, query)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                // GARANTE QUE NAO SERA NULO, USANDO UMA LISTA VAZIA COMO PADRÃO
                searchResults = response.results ?: emptyList()
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = "Erro ao buscar filmes: ${e.message}"
            )
        }
    }
}
