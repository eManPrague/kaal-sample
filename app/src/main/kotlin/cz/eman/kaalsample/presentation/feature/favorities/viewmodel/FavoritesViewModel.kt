package cz.eman.kaalsample.presentation.feature.favorities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.presentation.viewmodel.BaseViewModel
import cz.eman.kaalsample.domain.feature.movies.favorite.usecase.GetFavoriteMoviesUseCase
import cz.eman.kaalsample.presentation.feature.favorities.states.FavoriteMoviesViewStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  @author stefan.toth@eman.cz
 */
class FavoritesViewModel(
    private val getFavoriteMovies: GetFavoriteMoviesUseCase
) : BaseViewModel() {

    val viewState = MutableLiveData<FavoriteMoviesViewStates>()

    init {
        reset()
    }

    fun reset() {
        viewState.value = FavoriteMoviesViewStates.NotInitialized
    }

    fun loadFavoriteMovies() {
        viewState.value = FavoriteMoviesViewStates.Loading
        viewModelScope.launch {
            when (val result = withContext(Dispatchers.IO) { getFavoriteMovies(Unit) }) {
                is Result.Success -> {
                    viewState.value = FavoriteMoviesViewStates.MoviesLoaded(result.data)
                }
                is Result.Error -> {
                    viewState.value = FavoriteMoviesViewStates.LoadingError(result.error)
                }
            }
        }
    }

    fun invalidate() {
        viewState.value = FavoriteMoviesViewStates.Invalid
    }

    fun checkIfValidData() {
        if (viewState.value == FavoriteMoviesViewStates.Invalid) {
            reset()
        }
    }

}