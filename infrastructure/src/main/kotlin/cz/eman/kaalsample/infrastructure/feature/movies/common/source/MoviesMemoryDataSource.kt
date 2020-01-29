package cz.eman.kaalsample.infrastructure.feature.movies.common.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.movies.common.MoviesCache
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.source.MoviesDataSource
import cz.eman.kaalsample.infrastructure.core.MovieErrorCode

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class MoviesMemoryDataSource(private val moviesCache: MoviesCache) : MoviesDataSource {

    override suspend fun search(query: String): Result<List<Movie>> {
        val movies = moviesCache.search(query)
        return if (movies.isNotEmpty()) {
            Result.Success(movies)
        } else {
            Result.error(
                errorCode = MovieErrorCode.NO_MOVIES_IN_CACHE,
                message = "No data available in a memory cache"
            )
        }
    }

    override suspend fun getMovieById(movieId: Int): Result<Movie> {
        val movie = moviesCache.getMovieById(movieId)
        return if (movie != null) {
            Result.Success(movie)
        } else {
            Result.error(
                errorCode = MovieErrorCode.NO_MOVIES_IN_CACHE,
                message = "No data available in a memory cache"
            )
        }
    }

}