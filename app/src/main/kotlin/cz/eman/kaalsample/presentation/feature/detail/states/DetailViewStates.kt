package cz.eman.kaalsample.presentation.feature.detail.states

import cz.eman.kaal.domain.ErrorResult
import features.movies.common.model.Movie

/**
 *  @author stefan.toth@eman.cz
 */
sealed class DetailViewStates(val showLoading: Boolean) {

    object NotInitialized : DetailViewStates(showLoading = false)

    object Loading : DetailViewStates(showLoading = true)

    class MovieLoaded(val movie: Movie) : DetailViewStates(showLoading = false)

    class LoadingError(val error: ErrorResult) : DetailViewStates(showLoading = false)

    object Invalid : DetailViewStates(showLoading = false)
}