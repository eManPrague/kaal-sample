package cz.eman.kaalsample.presentation.feature.favorities.di

import cz.eman.kaalsample.presentation.feature.favorities.viewmodel.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  @author stefan.toth@eman.cz
 */
val favoritesModule = module {

    viewModel {
        FavoritesViewModel(
            getFavoriteMovies = get()
        )
    }
}