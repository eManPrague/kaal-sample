package features.movies.popular.usecase

import cz.eman.kaal.domain.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import features.movies.common.model.Movie
import features.movies.common.repository.MoviesRepository

/**
 * @author PavelHabzansky (pavel.habzansky@eman.cz) 2019-08-06.
 */
class GetPopularMoviesUseCase(private val moviesRepository: MoviesRepository) : UseCaseResult<List<Movie>, Unit>() {

    override suspend fun doWork(params: Unit): Result<List<Movie>> {
        return moviesRepository.getPopularMovies()
    }

}