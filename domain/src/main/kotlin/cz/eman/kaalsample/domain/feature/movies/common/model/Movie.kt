package cz.eman.kaalsample.domain.feature.movies.common.model

data class Movie(
        val adult: Boolean,
        var backdropPath: String?,
        val id: Int = -1,
        val originalTitle: String = "",
        val originalLanguage: String = "",
        val releaseDate: String? = "",
        var posterPath: String? = null,
        val popularity: Double = 0.0,
        val title: String = "",
        val voteAverage: Double = 0.0,
        val voteCount: Int = 0,
        val overview: String = "",
        val movieDetail: MovieDetail? = null
)