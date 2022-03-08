package cz.eman.kaalsample.presentation.feature.search.di

import cz.eman.kaalsample.presentation.feature.search.viewmodel.SearchMovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  @author stefan.toth@eman.cz
 */
val searchMovieModule = module {

    viewModel {
        SearchMovieViewModel(
                getPopularMovies = get(),
                searchMovies = get()
        )
    }
}