package cz.eman.kaalsample.app.di

import com.squareup.picasso.Picasso
import cz.eman.kaalsample.data.feature.movies.common.repository.MoviesRepositoryImpl
import cz.eman.kaalsample.data.feature.usermanagement.repository.UserRepositoryImpl
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository
import cz.eman.kaalsample.domain.feature.movies.favorite.usecase.GetFavoriteMoviesUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.repository.UserRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.AuthorizeUserUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.RegisterUserUseCase
import cz.eman.kaalsample.infrastructure.file.image.PicassoImageLoader
import org.koin.dsl.module


/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
val appModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(
                favoritesMovieDataSource = get(),
                movieCache = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(userDataSource = get())
    }


    single { GetFavoriteMoviesUseCase(moviesRepository = get()) }

    single { AuthorizeUserUseCase(userRepository = get()) }

    single { RegisterUserUseCase(userRepository = get()) }

    single { PicassoImageLoader(Picasso.with(get())) }

}