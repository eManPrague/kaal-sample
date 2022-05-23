package cz.eman.kaalsample.presentation.feature.login.di

import cz.eman.kaalsample.presentation.feature.login.viewModel.LoginViewModel
import cz.eman.kaalsample.presentation.feature.login.viewModel.PasswordViewModel
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
        PasswordViewModel(
            checkPassStrength = get()
        )
    }
}