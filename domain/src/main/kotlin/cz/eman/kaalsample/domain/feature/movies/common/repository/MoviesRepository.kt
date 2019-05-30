package cz.eman.kaalsample.domain.feature.movies.common.repository

import cz.eman.kaal.domain.Result
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie

/**
 * @author eMan s.r.o. (vaclav.souhrada@eman.cz)
 */
interface MoviesRepository {

    suspend fun getPopularMovies(): Result<List<Movie>>

    suspend fun getFavoriteMovies(): Result<List<Movie>>

    suspend fun checkFavoriteStatus(id: Int): Boolean

    suspend fun changeFavoriteStatus(movie: Movie, newFavoriteStatus: Boolean)

    suspend fun getMovieById(id: Int): Result<Movie>
}