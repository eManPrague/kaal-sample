package cz.eman.kaalsample.presentation.feature.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.detail.usecase.GetMovieByIdUseCase
import cz.eman.kaalsample.domain.feature.movies.favorite.usecase.ChangeFavoriteStatusUseCase
import cz.eman.kaalsample.domain.feature.movies.favorite.usecase.CheckMovieFavoriteStatusUseCase
import cz.eman.kaalsample.domain.feature.movies.favorite.usecase.Params
import cz.eman.kaalsample.presentation.feature.detail.states.DetailViewStates
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
) : KaalViewModel() {

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