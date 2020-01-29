package cz.eman.kaalsample.presentation.feature.popularmovies.states

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie

/**
 *  @author stefan.toth@eman.cz
 */
sealed class PopularMoviesViewStates(val showLoading: Boolean) {

    object NotInitialized : PopularMoviesViewStates(showLoading = false)

    /*object Loading : PopularMoviesViewStates(showLoading = true)

    class MoviesLoaded(val movieList: List<Movie>) : PopularMoviesViewStates(showLoading = false)*/

    class LoadingError(val error: ErrorResult) : PopularMoviesViewStates(showLoading = false)

    object Invalid : PopularMoviesViewStates(showLoading = false)
}