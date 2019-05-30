package cz.eman.kaalsample.domain.feature.movies.detail.usecase

import cz.eman.kaal.domain.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository

/**
 *  @author stefan.toth@eman.cz
 */
class GetMovieByIdUseCase(private val moviesRepository: MoviesRepository) : UseCaseResult<Movie, Int>() {

    override suspend fun doWork(id: Int): Result<Movie> {
        return moviesRepository.getMovieById(id)
    }
}