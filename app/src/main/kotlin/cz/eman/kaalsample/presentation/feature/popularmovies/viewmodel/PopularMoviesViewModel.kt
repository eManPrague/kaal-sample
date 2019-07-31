package cz.eman.kaalsample.presentation.feature.popularmovies.viewmodel

import androidx.lifecycle.viewModelScope
import cz.eman.kaal.domain.Result
import cz.eman.kaal.presentation.viewmodel.BaseViewModel
import cz.eman.kaalsample.domain.feature.movies.popular.usecase.GetPopularMoviesUseCase
import cz.eman.kaalsample.presentation.feature.popularmovies.states.PopularMoviesViewStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 *  @author stefan.toth@eman.cz
 */
class PopularMoviesViewModel(
        private val getPopularMovies: GetPopularMoviesUseCase
) : BaseViewModel() {

    val viewState = ConflatedBroadcastChannel<PopularMoviesViewStates>()

    init {
        reset()
    }

    fun reset() {
        Timber.v("Reset popular movies view model")
        viewState.offer(PopularMoviesViewStates.NotInitialized)
    }

    fun loadPopularMovies() {
        viewState.offer(PopularMoviesViewStates.Loading)
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getPopularMovies(Unit) }
            when (result) {
                is Result.Success -> {
                    viewState.offer(PopularMoviesViewStates.MoviesLoaded(result.data))
                }
                is Result.Error -> {
                    viewState.offer(PopularMoviesViewStates.LoadingError(result.error))
                }
            }
        }
    }

    fun checkIfValidData() {
        if (viewState.value == PopularMoviesViewStates.Invalid) {
            reset()
        }
    }

    fun invalidate() {
        viewState.offer(PopularMoviesViewStates.Invalid)
    }

}