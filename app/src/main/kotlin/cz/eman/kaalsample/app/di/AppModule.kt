package cz.eman.kaalsample.app.di

import com.squareup.picasso.Picasso
import cz.eman.kaalsample.data.feature.movies.common.repository.MoviesRepositoryImpl
import cz.eman.kaalsample.data.feature.usermanagement.repository.SecurityRepositoryImpl
import cz.eman.kaalsample.data.feature.usermanagement.repository.UserRepositoryImpl
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository
import cz.eman.kaalsample.domain.feature.movies.favorite.usecase.GetFavoriteMoviesUseCase
import cz.eman.kaalsample.domain.feature.movies.popular.usecase.GetPopularMoviesUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
import cz.eman.kaalsample.domain.feature.usermanagement.repository.UserRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.AuthorizeUserUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPasswordStrengthUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.RegisterUserUseCase
import cz.eman.kaalsample.infrastructure.core.di.DiInfrastructure
import cz.eman.kaalsample.infrastructure.feature.usermanagement.source.LocalSecurityDataSourceImpl
import cz.eman.kaalsample.infrastructure.feature.usermanagement.source.RemoteSecurityDataSourceImpl
import cz.eman.kaalsample.infrastructure.file.image.PicassoImageLoader
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.security.Security


/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
val appModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(
                movieRemoteDataSource = get(named(DiInfrastructure.DATA_STORE_REMOTE)),
                memoryMovieDataSource = get(named(DiInfrastructure.DATA_STORE_IN_MEMORY)),
                favoritesMovieDataSource = get(),
                movieCache = get()
        )
    }

    // Added this
    single<SecurityRepository> {
        SecurityRepositoryImpl(
            remoteSecurityDataSource = RemoteSecurityDataSourceImpl(),
            localSecurityDataSource = RemoteSecurityDataSourceImpl() //How to solve with Dao injection?
        )
    }

    single<UserRepository> { UserRepositoryImpl(
            userDataSource = get()
        )
    }

    single { GetPopularMoviesUseCase(moviesRepository = get()) }

    single { GetFavoriteMoviesUseCase(moviesRepository = get()) }

    single { AuthorizeUserUseCase(userRepository = get()) }

    single { RegisterUserUseCase(userRepository = get()) }

    single { PicassoImageLoader(Picasso.with(get())) }

    single { CheckPasswordStrengthUseCase(securityRepository = get()) }
}