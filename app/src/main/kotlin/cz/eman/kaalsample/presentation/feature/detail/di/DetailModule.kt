package cz.eman.kaalsample.presentation.feature.detail.di

import cz.eman.kaalsample.domain.feature.movies.detail.usecase.GetMovieByIdUseCase
import cz.eman.kaalsample.domain.feature.movies.favorite.usecase.ChangeFavoriteStatusUseCase
import cz.eman.kaalsample.domain.feature.movies.favorite.usecase.CheckMovieFavoriteStatusUseCase
import cz.eman.kaalsample.presentation.feature.detail.viewmodel.DetailViewModel
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