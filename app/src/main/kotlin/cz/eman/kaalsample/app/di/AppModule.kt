package cz.eman.kaalsample.app.di

import com.squareup.picasso.Picasso
import cz.eman.kaalsample.data.feature.movies.common.repository.MoviesRepositoryImpl
import cz.eman.kaalsample.data.feature.usermanagement.repository.UserRepositoryImpl
import cz.eman.kaalsample.infrastructure.core.di.DiInfrastructure
import cz.eman.kaalsample.infrastructure.file.image.PicassoImageLoader
import features.movies.common.repository.MoviesRepository
import features.movies.favorite.usecase.GetFavoriteMoviesUseCase
import features.movies.popular.usecase.GetPopularMoviesUseCase
import features.usermanagement.repository.UserRepository
import features.usermanagement.usecases.AuthorizeUserUseCase
import features.usermanagement.usecases.RegisterUserUseCase
import org.koin.dsl.module


/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
val appModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(
                movieRemoteDataSource = get(DiInfrastructure.DATA_STORE_REMOTE),
                memoryMovieDataSource = get(DiInfrastructure.DATA_STORE_IN_MEMORY),
                favoritesMovieDataSource = get(),
                movieCache = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(userDataSource = get())
    }

    single { GetPopularMoviesUseCase(moviesRepository = get()) }

    single { GetFavoriteMoviesUseCase(moviesRepository = get()) }

    single { AuthorizeUserUseCase(userRepository = get()) }

    single { RegisterUserUseCase(userRepository = get()) }

    single { PicassoImageLoader(Picasso.with(get())) }

}