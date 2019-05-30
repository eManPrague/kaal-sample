package cz.eman.kaalsample.domain.feature.movies.favorite.usecase

import cz.eman.kaal.domain.usecases.UseCaseResultNoParams
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class GetFavoriteMoviesUseCase(private val moviesRepository: MoviesRepository) :
    UseCaseResultNoParams<List<Movie>>() {

    override suspend fun doWork(params: Unit) = moviesRepository.getFavoriteMovies()
}