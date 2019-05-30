package cz.eman.kaalsample.domain.feature.movies.favorite.usecase

import cz.eman.kaal.domain.usecases.UseCase
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository

/**
 *  @author stefan.toth@eman.cz
 */
class CheckMovieFavoriteStatusUseCase(private val moviesRepository: MoviesRepository) : UseCase<Boolean, Int>() {

    override suspend fun doWork(params: Int): Boolean {
        return moviesRepository.checkFavoriteStatus(params)
    }

}