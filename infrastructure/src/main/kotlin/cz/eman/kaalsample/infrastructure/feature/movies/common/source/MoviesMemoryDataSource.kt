package cz.eman.kaalsample.infrastructure.feature.movies.common.source

import cz.eman.kaal.domain.Result
import cz.eman.kaalsample.infrastructure.core.ErrorCode
import cz.eman.kaalsample.infrastructure.core.ErrorCodeResult
import features.movies.common.MoviesCache
import features.movies.common.model.Movie
import features.movies.common.source.MoviesDataSource

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class MoviesMemoryDataSource(private val moviesCache: MoviesCache) : MoviesDataSource {

    override suspend fun getPopularMovies(): Result<List<Movie>> {
        val movies = moviesCache.getAll()
        return if (movies.isNotEmpty()) {
            Result.Success(movies)
        } else {
            Result.Error(
                ErrorCodeResult(
                    code = ErrorCode.NO_MOVIES_IN_CACHE,
                    message = "No data available in a memory cache"
                )
            )
        }
    }

    override suspend fun search(query: String): Result<List<Movie>> {
        val movies = moviesCache.search(query)
        return if (movies.isNotEmpty()) {
            Result.Success(movies)
        } else {
            Result.Error(
                ErrorCodeResult(
                    code = ErrorCode.NO_MOVIES_IN_CACHE,
                    message = "No data available in a memory cache"
                )
            )
        }
    }

    override suspend fun getMovieById(movieId: Int): Result<Movie> {
        val movie = moviesCache.getMovieById(movieId)
        return movie?.let { Result.Success(it) } ?: run {
            Result.Error(
                ErrorCodeResult(
                    code = ErrorCode.NO_MOVIES_IN_CACHE,
                    message = "No data available in a memory cache"
                )
            )
        }
    }

}