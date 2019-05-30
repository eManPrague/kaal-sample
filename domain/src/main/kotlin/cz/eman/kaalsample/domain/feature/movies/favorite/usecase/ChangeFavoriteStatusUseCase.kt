package cz.eman.kaalsample.domain.feature.movies.favorite.usecase

import cz.eman.kaal.domain.usecases.UseCase
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository

/**
 *  @author stefan.toth@eman.cz
 */
class ChangeFavoriteStatusUseCase(private val moviesRepository: MoviesRepository) : UseCase<Unit, Params>() {

    override suspend fun doWork(params: Params) {
        return moviesRepository.changeFavoriteStatus(params.movie, params.isFavorite)
    }
}

data class Params(val movie: Movie, val isFavorite: Boolean)