package cz.eman.kaalsample.presentation.feature.popularmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import cz.eman.kaal.presentation.viewmodel.BaseViewModel
import cz.eman.kaalsample.presentation.feature.popularmovies.states.PopularMoviesViewStates
import timber.log.Timber

/**
 *  @author stefan.toth@eman.cz
 */
class PopularMoviesViewModel : BaseViewModel() {

    val viewState = MutableLiveData<PopularMoviesViewStates>()

    init {
        reset()
    }

    fun reset() {
        Timber.v("Reset popular movies view model")
        viewState.value = PopularMoviesViewStates.NotInitialized
    }

    fun checkIfValidData() {
        if (viewState.value == PopularMoviesViewStates.Invalid) {
            reset()
        }
    }

    fun invalidate() {
        viewState.value = PopularMoviesViewStates.Invalid
    }

}