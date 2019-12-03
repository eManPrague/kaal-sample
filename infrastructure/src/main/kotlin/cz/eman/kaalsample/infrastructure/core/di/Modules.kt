package cz.eman.kaalsample.infrastructure.core.di

import cz.eman.kaalsample.domain.feature.movies.common.MoviesCache
import cz.eman.kaalsample.domain.feature.movies.favorite.source.FavoritesMovieDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.source.UserDataSource
import cz.eman.kaalsample.infrastructure.core.room.MovieDatabase
import cz.eman.kaalsample.domain.feature.movies.common.source.MoviesDataSource
import cz.eman.kaalsample.infrastructure.core.di.DiInfrastructure
import cz.eman.kaalsample.infrastructure.feature.movies.common.apiservice.MOVIE_DB_HOST
import cz.eman.kaalsample.infrastructure.feature.movies.common.apiservice.MovieApiService
import cz.eman.kaalsample.infrastructure.feature.movies.common.apiservice.interceptor.ServerRequestInterceptor
import cz.eman.kaalsample.infrastructure.feature.movies.common.cache.MemoryMovieCache
import cz.eman.kaalsample.infrastructure.feature.movies.common.source.MoviesMemoryDataSource
import cz.eman.kaalsample.infrastructure.feature.movies.common.source.MoviesRemoteSource
import cz.eman.kaalsample.infrastructure.feature.movies.favorite.source.FavoritesMovieLocalDataSource
import cz.eman.kaalsample.infrastructure.feature.usermanagement.source.UserLocalDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val allApiModules by lazy { listOf(databaseModule, apiServiceModule) }

val apiServiceModule = module {

    single { provideRetrofit() }

    single<MovieApiService> { provideMovieApiService(retrofit = get()) }

    single<MoviesCache> { MemoryMovieCache() }

    single<MoviesDataSource>(named(DiInfrastructure.DATA_STORE_REMOTE)) {
        MoviesRemoteSource(
            movieApiService = get()
        )
    }

    single<MoviesDataSource>(named(DiInfrastructure.DATA_STORE_IN_MEMORY)) {
        MoviesMemoryDataSource(
            moviesCache = get()
        )
    }

    single<UserDataSource> { UserLocalDataSource(userDao = get<MovieDatabase>().userDao)}

}

val databaseModule = module {

/*    single {
        Room.databaseBuilder(get(), MovieDatabase::class.java, "kaal-movie.db").build()
    }*/

    single { MovieDatabase.getInstance(context = get(), factory = null) }

    single<FavoritesMovieDataSource> {
        FavoritesMovieLocalDataSource(favoriteMovieDao = get<MovieDatabase>().favoriteMovieDao)
    }
}

private fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
    return retrofit.create(MovieApiService::class.java)
}

private fun provideRetrofit(): Retrofit {
    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.addInterceptor(ServerRequestInterceptor)

    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    clientBuilder.interceptors().add(logging)

    return Retrofit.Builder()
        .baseUrl(MOVIE_DB_HOST)
        .client(clientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

