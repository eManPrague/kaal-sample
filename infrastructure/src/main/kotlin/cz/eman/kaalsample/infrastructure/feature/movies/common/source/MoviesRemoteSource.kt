package cz.eman.kaalsample.infrastructure.feature.movies.common.source

import cz.eman.kaal.infrastructure.common.callResult
import cz.eman.kaalsample.domain.feature.movies.common.source.MoviesDataSource
import cz.eman.kaalsample.infrastructure.feature.movies.common.apiservice.MovieApiService
import cz.eman.kaalsample.infrastructure.feature.movies.common.mapper.MoviesMapper

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class MoviesRemoteSource(private val movieApiService: MovieApiService) : MoviesDataSource {

    override suspend fun getPopularMovies() = callResult(
        responseCall = { movieApiService.getPopularMovies() },
        errorMessage = { "Cannot get Popular movies" }
    ) {
        MoviesMapper.mapWrapperToMovie(it)
    }

    override suspend fun search(query: String) = callResult(
        responseCall = { movieApiService.searchMovies(query) },
        errorMessage = { "Cannot find movies by query[$query]" }
    ) {
        MoviesMapper.mapWrapperToMovie(it)
    }

    override suspend fun getMovieById(movieId: Int) = callResult(
        responseCall = { movieApiService.getMovieDetails(movieId) },
        errorMessage = { "Cannot get movie by movieId[$movieId]" }
    ) {
        MoviesMapper.mapToMovieDetails(it)
    }
}