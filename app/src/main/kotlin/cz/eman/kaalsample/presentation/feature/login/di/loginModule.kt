package cz.eman.kaalsample.presentation.feature.login.di

import cz.eman.kaalsample.presentation.feature.login.viewModel.LoginViewModel
import cz.eman.kaalsample.presentation.feature.login.viewModel.SecucityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  @author stefan.toth@eman.cz
 */
val loginModule = module {

    viewModel {
        LoginViewModel(
            authoriseUser = get(),
            registerUser = get()
        )
    }

    viewModel {
        SecucityViewModel(
            checkPswdStrength = get()
        )
    }
}