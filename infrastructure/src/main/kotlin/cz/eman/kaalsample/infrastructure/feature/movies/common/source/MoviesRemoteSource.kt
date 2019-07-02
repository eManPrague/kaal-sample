package cz.eman.kaalsample.infrastructure.feature.movies.common.source

import cz.eman.kaal.domain.ApiErrorResult
import cz.eman.kaal.domain.Result
import cz.eman.kaal.domain.callSafe
import cz.eman.kaal.domain.exception.EmptyBodyException
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.source.MoviesDataSource
import cz.eman.kaalsample.infrastructure.feature.movies.common.apiservice.MovieApiService
import cz.eman.kaalsample.infrastructure.feature.movies.common.mapper.MoviesMapper
import cz.eman.logger.logError
import retrofit2.awaitResponse

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class MoviesRemoteSource(private val movieApiService: MovieApiService) : MoviesDataSource {

    override suspend fun getPopularMovies() = callSafe(
            call = { fetchPopularMovies() },
            errorMessage = "Cannot fetch popular movies"
    )

    override suspend fun search(query: String) = callSafe(
            call = { searchMovies(query) },
            errorMessage = "Cannot find movies by query[$query]"
    )

    override suspend fun getMovieById(movieId: Int): Result<Movie> = callSafe(
            call = { findMovieById(movieId) },
            errorMessage = "Cannot get movie by movieId[$movieId]"
    )

    private suspend fun fetchPopularMovies(): Result<List<Movie>> {
        val response = movieApiService.getPopularMovies().awaitResponse()

        return if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                Result.Success(data = MoviesMapper.mapWrapperToMovie(body))
            } ?: run {
                Result.Error(
                        ApiErrorResult(
                                code = response.code(),
                                errorMessage = response.message(),
                                apiThrowable = EmptyBodyException()
                        )
                )
            }
        } else {
            val errorResult =
                    ApiErrorResult(code = response.code(), errorMessage = response.message())
            logError { "Cannot fetch popular movies[$errorResult]" }
            Result.Error(errorResult)
        }
    }

    private suspend fun searchMovies(query: String): Result<List<Movie>> {
        val response = movieApiService.searchMovies(query).awaitResponse()

        return if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                Result.Success(data = MoviesMapper.mapWrapperToMovie(body))
            } ?: run {
                Result.Error(
                        ApiErrorResult(
                                code = response.code(),
                                errorMessage = response.message(),
                                apiThrowable = EmptyBodyException()
                        )
                )
            }

        } else {
            val errorResult =
                    ApiErrorResult(code = response.code(), errorMessage = response.message())
            logError { "Cannot search movies by query with error result[$errorResult]" }

            Result.Error(errorResult)
        }
    }

    private suspend fun findMovieById(movieId: Int): Result<Movie> {
        val response = movieApiService.getMovieDetails(movieId).awaitResponse()

        return if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                Result.Success(data = MoviesMapper.mapToMovieDetails(body))
            } ?: run {
                Result.Error(
                        ApiErrorResult(
                                code = response.code(),
                                errorMessage = response.message(),
                                apiThrowable = EmptyBodyException()
                        )
                )
            }
        } else {
            val errorResult =
                    ApiErrorResult(code = response.code(), errorMessage = response.message())
            logError { "Cannot find movie[$movieId] with error result[$errorResult]" }

            Result.Error(errorResult)
        }
    }
}