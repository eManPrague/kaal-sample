package cz.eman.kaalsample.presentation.feature.splash.di

import cz.eman.kaalsample.presentation.feature.splash.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  @author stefan.toth@eman.cz
 */
val splashModule = module {

    viewModel {
        SplashViewModel(

        )
    }
}