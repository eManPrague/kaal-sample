package cz.eman.kaalsample.domain.feature.movies.search.usecase

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository

class SearchMoviesUseCase (private val moviesRepository: MoviesRepository) : UseCaseResult<List<Movie>, String>() {

    override suspend fun doWork(params: String): Result<List<Movie>> {
        return moviesRepository.searchMovieByTitle(params)
    }
}