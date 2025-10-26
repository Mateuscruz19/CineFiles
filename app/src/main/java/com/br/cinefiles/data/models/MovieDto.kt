package com.br.cinefiles.data.models

import com.google.gson.annotations.SerializedName

data class MovieDto(
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
    
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    
    @SerializedName("release_date")
    val releaseDate: String?
)

