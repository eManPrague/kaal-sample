package cz.eman.kaalsample.presentation.feature.popularmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.domain.Result
import cz.eman.kaal.presentation.viewmodel.BaseViewModel
import cz.eman.kaalsample.domain.feature.movies.popular.usecase.GetPopularMoviesUseCase
import cz.eman.kaalsample.presentation.feature.popularmovies.states.PopularMoviesViewStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 *  @author stefan.toth@eman.cz
 */
class PopularMoviesViewModel(
    private val getPopularMovies: GetPopularMoviesUseCase
) : BaseViewModel() {

    val viewState = MutableLiveData<PopularMoviesViewStates>()

    init {
        reset()
    }

    fun reset() {
        Timber.v("Reset popular movies view model")
        viewState.value = PopularMoviesViewStates.NotInitialized
    }

    fun loadPopularMovies() {
        viewState.value = PopularMoviesViewStates.Loading
        viewModelScope.launch {
            when (val result = withContext(Dispatchers.IO) { getPopularMovies(Unit) }) {
                is Result.Success -> {
                    viewState.value = PopularMoviesViewStates.MoviesLoaded(result.data)
                }
                is Result.Error -> {
                    viewState.value = PopularMoviesViewStates.LoadingError(result.error)
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
        viewState.value = PopularMoviesViewStates.Invalid
    }

}