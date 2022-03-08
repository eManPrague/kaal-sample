package cz.eman.kaalsample.presentation.feature.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.domain.feature.movies.popular.usecase.GetPopularMoviesUseCase
import cz.eman.kaalsample.domain.feature.movies.search.usecase.SearchMoviesUseCase
import cz.eman.kaalsample.presentation.feature.search.states.SearchMovieViewStates
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/**
 *  @author stefan.toth@eman.cz
 */
class SearchMovieViewModel(
    private val getPopularMovies: GetPopularMoviesUseCase,
    private val searchMovies: SearchMoviesUseCase
) : KaalViewModel() {

    val viewState = MutableLiveData<SearchMovieViewStates>()

    init {
        reset()
    }

    fun reset() {
        Timber.v("Reset popular movies view model")
        viewState.value = SearchMovieViewStates.NotInitialized
    }

    fun loadMovies(query: String? = null) {
        viewState.value = SearchMovieViewStates.Loading

        launch {
            val result = withContext(Dispatchers.IO) {
                if (query.isNullOrBlank())
                    getPopularMovies(Unit)
                else
                    searchMovies(query)
            }
            when (result) {
                is Result.Success -> {
                    viewState.value = SearchMovieViewStates.MoviesLoaded(result.data)
                }
                is Result.Error -> {
                    viewState.value = SearchMovieViewStates.LoadingError(result.error)
                }
            }
        }
    }

    fun checkIfValidData() {
        if (viewState.value == SearchMovieViewStates.Invalid) {
            reset()
        }
    }

    fun invalidate() {
        viewState.value = SearchMovieViewStates.Invalid
    }

}