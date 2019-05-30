package cz.eman.kaalsample.infrastructure.feature.movies.favorite.mapper

import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.infrastructure.feature.movies.favorite.db.entity.FavoriteMovieEntity

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
object FavoriteMovieMapper {

    fun mapToDbEntity(movie: Movie) = FavoriteMovieEntity(
        id = movie.id,
        voteCount = movie.voteCount,
        voteAverage = movie.voteAverage,
        title = movie.title,
        releaseDate = movie.releaseDate,
        posterPath = movie.posterPath,
        popularity = movie.popularity,
        overview = movie.overview,
        originalTitle = movie.originalTitle,
        originalLanguage = movie.originalLanguage,
        backdropPath = movie.backdropPath,
        adult = movie.adult
    )

    fun mapToMovie(entity: FavoriteMovieEntity) = Movie(
        id = entity.id,
        voteCount = entity.voteCount,
        voteAverage = entity.voteAverage,
        title = entity.title,
        releaseDate = entity.releaseDate,
        posterPath = entity.posterPath,
        popularity = entity.popularity,
        overview = entity.overview,
        originalTitle = entity.originalTitle,
        originalLanguage = entity.originalLanguage,
        backdropPath = entity.backdropPath,
        adult = entity.adult
    )
}