package features.movies.favorite.usecase

import cz.eman.kaal.domain.usecases.UseCase
import features.movies.common.model.Movie
import features.movies.common.repository.MoviesRepository

/**
 * @author PavelHabzansky (pavel.habzansky@eman.cz) 2019-08-06.
 */
class ChangeFavoriteStatusUseCase(private val moviesRepository: MoviesRepository) : UseCase<Unit, Params>() {

    override suspend fun doWork(params: Params) {
        return moviesRepository.changeFavoriteStatus(params.movie, params.isFavorite)
    }
}

data class Params(val movie: Movie, val isFavorite: Boolean)