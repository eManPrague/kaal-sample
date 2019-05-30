package cz.eman.kaalsample.infrastructure.feature.movies.common.mapper

import cz.eman.kaalsample.domain.feature.movies.common.model.*
import cz.eman.kaalsample.infrastructure.feature.movies.common.model.*

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[MoviesListResult]
 */
object MoviesMapper {

    const val POSTER_W342_URL = "https://image.tmdb.org/t/p/w342"
    const val BACKDROP_W780_URL = "https://image.tmdb.org/t/p/w780"
    const val YOUTUBE_URL = "https://www.youtube.com/watch?v="

    fun mapWrapperToMovie(from: MoviesWrapperDto): List<Movie> {
        return from.results.mapTo(mutableListOf()) { from ->
            Movie(
                adult = from.adult,
                id = from.id,
                originalTitle = from.originalTitle,
                originalLanguage = from.originalLanguage,
                releaseDate = from.releaseDate,
                posterPath = from.posterPath?.let { POSTER_W342_URL + it },
                backdropPath = from.backdropPath?.let { BACKDROP_W780_URL + it },
                popularity = from.popularity,
                title = from.title,
                voteAverage = from.voteAverage,
                overview = from.overview ?: ""
            )
        }
    }

    fun mapToMovieDetails(from: MovieDetailDto) = Movie(
        adult = from.adult,
        id = from.id,
        originalTitle = from.originalTitle,
        originalLanguage = from.originalLanguage,
        releaseDate = from.releaseDate,
        posterPath = from.posterPath?.let { POSTER_W342_URL + it },
        backdropPath = from.backdropPath?.let { BACKDROP_W780_URL + it },
        popularity = from.popularity,
        title = from.title,
        voteAverage = from.voteAverage,
        overview = from.overview,
        movieDetail = MovieDetail(
            adult = from.adult,
            backdropPath = from.backdropPath,
            budget = from.budget,
            genres = from.genres.map { mapGenreToDomain(it) },
            homepage = from.homepage,
            id = from.id,
            imdbId = from.imdbId,
            originalLanguage = from.originalLanguage,
            originalTitle = from.originalTitle,
            overview = from.overview,
            popularity = from.popularity,
            posterPath = from.posterPath,
            releaseDate = from.releaseDate,
            revenue = from.revenue,
            reviews = from.reviews.results.map { mapReviewsToDomain(it) },
            runtime = from.runtime,
            tagline = from.tagline,
            title = from.title,
            video = from.video,
            videos = from.videos.results.map { mapVideoToDomain(it) },
            voteAverage = from.voteAverage,
            voteCount = from.voteCount
        )
    )

    private fun mapVideoToDomain(video: VideoDto) = Video(
        id = video.id,
        name = video.name,
        key = video.key,
        site = video.site,
        type = video.type
    )

    private fun mapReviewsToDomain(review: Review) =
        Review(id = review.id, author = review.author, content = review.content)

    private fun mapGenreToDomain(genre: GenreData) = Genre(id = genre.id, name = genre.name)

}