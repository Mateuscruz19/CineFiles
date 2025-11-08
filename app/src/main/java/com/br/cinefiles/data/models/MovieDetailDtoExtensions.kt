package com.br.cinefiles.data.models

fun MovieDetailDto.toMovieDto(): MovieDto {
    return MovieDto(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        voteAverage = this.voteAverage,
        genreIds = this.genres?.map { it.id } ?: emptyList(),
        releaseDate = this.releaseDate
    )
}
