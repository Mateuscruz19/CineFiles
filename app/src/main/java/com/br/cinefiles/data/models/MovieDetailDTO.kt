package com.br.cinefiles.data.models


import com.google.gson.annotations.SerializedName

/**
 * Representa a resposta completa da API de detalhes do filme.
 */
data class MovieDetailDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("vote_average")
    val voteAverage: Double?,

    @SerializedName("release_date") // Usaremos para o "Ano"
    val releaseDate: String?,

    @SerializedName("genres") // Lista de objetos de Gênero
    val genres: List<GenreDto>?

    // "Direção" (Director) não vem nesta API.
    // É uma chamada separada para "movie/{id}/credits"
)

/**
 * Representa o objeto de Gênero que vem dentro do MovieDetailDto.
 */
data class GenreDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)
