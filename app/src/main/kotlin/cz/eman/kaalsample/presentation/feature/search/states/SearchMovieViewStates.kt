package cz.eman.kaalsample.presentation.feature.search.states

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie

/**
 *  @author stefan.toth@eman.cz
 */
sealed class SearchMovieViewStates(val showLoading: Boolean) {

    object NotInitialized : SearchMovieViewStates(showLoading = false)

    object Loading : SearchMovieViewStates(showLoading = true)

    class MoviesLoaded(val movieList: List<Movie>) : SearchMovieViewStates(showLoading = false)

    class LoadingError(val error: ErrorResult) : SearchMovieViewStates(showLoading = false)

    object Invalid : SearchMovieViewStates(showLoading = false)
}