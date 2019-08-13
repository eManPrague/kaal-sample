package cz.eman.kaalsample.presentation.feature.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.domain.Result
import cz.eman.kaal.presentation.viewmodel.BaseViewModel
import cz.eman.kaalsample.presentation.feature.detail.states.DetailViewStates
import features.movies.common.model.Movie
import features.movies.detail.GetMovieByIdUseCase
import features.movies.favorite.usecase.ChangeFavoriteStatusUseCase
import features.movies.favorite.usecase.CheckMovieFavoriteStatusUseCase
import features.movies.favorite.usecase.Params
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 *  @author stefan.toth@eman.cz
 */
class DetailViewModel(
        private val getMovieDetail: GetMovieByIdUseCase,
        private val checkFavoriteStatus: CheckMovieFavoriteStatusUseCase,
        private val changeFavoriteStatus: ChangeFavoriteStatusUseCase
) : BaseViewModel() {

    val viewState = MutableLiveData<DetailViewStates>()

    var isThisMovieInFavoriteList = false

    init {
        reset()
    }

    fun reset() {
        viewState.value = DetailViewStates.NotInitialized
    }

    fun loadPopularMovies(movieId: Int) {
        viewState.value = DetailViewStates.Loading
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                isThisMovieInFavoriteList = checkFavoriteStatus(movieId)
                getMovieDetail(movieId)
            }
            when (result) {
                is Result.Success -> {
                    viewState.value = DetailViewStates.MovieLoaded(result.data)
                }
                is Result.Error -> {
                    viewState.value = DetailViewStates.LoadingError(result.error)
                }
            }
        }
    }

    fun changeFavoriteStatus(movie: Movie, newState: Boolean) {
        Timber.d(" update favorite status to: $newState with movie: ${movie.title}")
        isThisMovieInFavoriteList = newState

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                changeFavoriteStatus(Params(movie, newState))
            }
        }
    }


    fun disconnect() {
        viewState.value = DetailViewStates.Invalid
    }
}