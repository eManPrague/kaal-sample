package cz.eman.kaalsample.presentation.feature.popularmovies.di

import cz.eman.kaalsample.presentation.feature.popularmovies.viewmodel.PopularMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  @author stefan.toth@eman.cz
 */
val popularMoviesModule = module {

    viewModel {
        PopularMoviesViewModel(
                getPopularMovies = get()
        )
    }
}