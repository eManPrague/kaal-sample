package features.movies.detail

import cz.eman.kaal.domain.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import features.movies.common.model.Movie
import features.movies.common.repository.MoviesRepository

/**
 * @author PavelHabzansky (pavel.habzansky@eman.cz) 2019-08-06.
 */
class GetMovieByIdUseCase(private val moviesRepository: MoviesRepository) : UseCaseResult<Movie, Int>() {

    override suspend fun doWork(id: Int): Result<Movie> {
        return moviesRepository.getMovieById(id)
    }
}