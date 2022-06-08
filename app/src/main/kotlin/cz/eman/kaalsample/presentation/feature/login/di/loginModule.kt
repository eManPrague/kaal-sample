package cz.eman.kaalsample.presentation.feature.login.di

import cz.eman.kaalsample.data.feature.security.SecurityRepositoryImpl
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.GetPswdUnsupportedCharsUseCase
import cz.eman.kaalsample.presentation.feature.login.viewModel.LoginViewModel
import cz.eman.kaalsample.presentation.feature.login.viewModel.SecurityViewModel
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
        SecurityViewModel(
            checkPswdStrength = get()
        )
    }

    factory { CheckPswdStrengthUseCase(getUnsupportedChars = get()) }
    factory { GetPswdUnsupportedCharsUseCase(repo = get()) }
    single<SecurityRepository> {
        SecurityRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }


}