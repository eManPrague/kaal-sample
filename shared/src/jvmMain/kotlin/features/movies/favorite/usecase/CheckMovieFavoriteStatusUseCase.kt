package features.movies.favorite.usecase

import cz.eman.kaal.domain.usecases.UseCase
import features.movies.common.repository.MoviesRepository

/**
 * @author PavelHabzansky (pavel.habzansky@eman.cz) 2019-08-06.
 */
class CheckMovieFavoriteStatusUseCase(private val moviesRepository: MoviesRepository) : UseCase<Boolean, Int>() {

    override suspend fun doWork(params: Int): Boolean {
        return moviesRepository.checkFavoriteStatus(params)
    }

}