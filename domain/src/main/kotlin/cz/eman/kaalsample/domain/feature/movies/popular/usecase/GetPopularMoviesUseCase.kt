package cz.eman.kaalsample.domain.feature.movies.popular.usecase

import cz.eman.kaal.domain.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaal.domain.usecases.UseCaseResultNoParams
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository

/**
 * By this use-case you can get current a list of the most popular movies in an asynchronous way.
 *
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[UseCase]
 */
class GetPopularMoviesUseCase(private val moviesRepository: MoviesRepository) : UseCaseResultNoParams<List<Movie>>() {

    override suspend fun doWork(params: Unit): Result<List<Movie>> {
        return moviesRepository.getPopularMovies()
    }
}