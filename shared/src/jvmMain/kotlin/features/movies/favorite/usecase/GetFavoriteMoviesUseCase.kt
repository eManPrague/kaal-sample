package features.movies.favorite.usecase

import cz.eman.kaal.domain.usecases.UseCaseResultNoParams
import features.movies.common.model.Movie
import features.movies.common.repository.MoviesRepository

/**
 * @author PavelHabzansky (pavel.habzansky@eman.cz) 2019-08-06.
 */
class GetFavoriteMoviesUseCase(private val moviesRepository: MoviesRepository) :
        UseCaseResultNoParams<List<Movie>>() {

    override suspend fun doWork(params: Unit) = moviesRepository.getFavoriteMovies()
}