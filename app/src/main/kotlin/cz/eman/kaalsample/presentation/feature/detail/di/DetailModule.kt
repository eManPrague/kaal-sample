package cz.eman.kaalsample.presentation.feature.detail.di

import cz.eman.kaalsample.presentation.feature.detail.viewmodel.DetailViewModel
import features.movies.detail.GetMovieByIdUseCase
import features.movies.favorite.usecase.ChangeFavoriteStatusUseCase
import features.movies.favorite.usecase.CheckMovieFavoriteStatusUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  @author stefan.toth@eman.cz
 */
val detailModule = module {

    single {
        GetMovieByIdUseCase(moviesRepository = get())
    }

    single {
        CheckMovieFavoriteStatusUseCase(moviesRepository = get())
    }

    single {
        ChangeFavoriteStatusUseCase(moviesRepository = get())
    }

    viewModel {
        DetailViewModel(
                getMovieDetail = get(),
                checkFavoriteStatus = get(),
                changeFavoriteStatus = get()
        )
    }
}