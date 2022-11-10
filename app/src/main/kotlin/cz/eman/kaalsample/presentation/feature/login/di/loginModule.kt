package cz.eman.kaalsample.presentation.feature.login.di

import cz.eman.kaalsample.data.feature.usermanagement.repository.PswdStrengthLocalRepositoryImpl
import cz.eman.kaalsample.data.feature.usermanagement.repository.PswdStrengthRemoteRepositoryImpl
import cz.eman.kaalsample.data.feature.usermanagement.repository.PswdStrengthRepositoryImpl
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthLocalDataSource
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthRemoteDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import cz.eman.kaalsample.presentation.feature.login.viewModel.LoginViewModel
import cz.eman.kaalsample.presentation.feature.login.viewModel.PswdStrengthViewModel
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

    // TODO: Ask if this init of DataSource is OK
    single<PswdStrengthLocalDataSource> {
        PswdStrengthLocalRepositoryImpl()
    }

    single<PswdStrengthRemoteDataSource> {
        PswdStrengthRemoteRepositoryImpl()
    }

    single<PswdStrengthRepository> {
        PswdStrengthRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }

    single {
        CheckPswdStrengthUseCase(repository = get())
    }

    viewModel {
        PswdStrengthViewModel(
            checkPswdStrength = get()
        )
    }
}