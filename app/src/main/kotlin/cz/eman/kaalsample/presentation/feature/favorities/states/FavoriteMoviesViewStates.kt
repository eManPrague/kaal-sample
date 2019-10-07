package cz.eman.kaalsample.presentation.feature.favorities.states

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie

/**
 *  @author stefan.toth@eman.cz
 */
sealed class FavoriteMoviesViewStates(val isThereContentToShow: Boolean) {

    object NotInitialized : FavoriteMoviesViewStates(false)

    object Loading : FavoriteMoviesViewStates(false)

    class MoviesLoaded(val movieList: List<Movie>) : FavoriteMoviesViewStates(true)

    class LoadingError(val error: ErrorResult) : FavoriteMoviesViewStates(true)

    object Invalid : FavoriteMoviesViewStates(true)
}