package cz.eman.kaalsample.domain.feature.movies.popular.usecase

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCase
import cz.eman.kaal.domain.usecases.UseCaseNoParams
import cz.eman.kaal.domain.usecases.UseCaseResultNoParams
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository

/**
 * By this use-case you can get current a list of the most popular movies in an asynchronous way.
 *
 * @author eMan s.r.o.
 */
class GetPopularMoviesUseCase(private val movieRepository: MoviesRepository) :
    UseCaseResultNoParams<List<Movie>>() {

    override suspend fun doWork(params: Unit): Result<List<Movie>> {
        return movieRepository.getPopularMovies()
    }

}