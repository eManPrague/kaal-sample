package cz.eman.kaalsample.data.feature.movies.common.source

import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaal.domain.result.Result

/**
 * @author eMan s.r.o. (vaclav.souhrada@eman.cz)
 */
interface MoviesDataSource {

    suspend fun getPopularMovies(): Result<List<Movie>>

    suspend fun search(query: String): Result<List<Movie>>

    suspend fun getMovieById(movieId: Int): Result<Movie>

}