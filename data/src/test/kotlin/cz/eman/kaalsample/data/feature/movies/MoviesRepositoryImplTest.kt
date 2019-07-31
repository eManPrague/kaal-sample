package cz.eman.kaalsample.data.feature.movies

import cz.eman.kaal.domain.ErrorResult
import cz.eman.kaal.domain.Result
import cz.eman.kaalsample.data.feature.movies.common.repository.MoviesRepositoryImpl
import cz.eman.kaalsample.domain.feature.movies.common.MoviesCache
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.source.MoviesDataSource
import cz.eman.kaalsample.domain.feature.movies.favorite.source.FavoritesMovieDataSource
import io.kotlintest.TestCase
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking

/**
 *  @author stefan.toth@eman.cz
 */
class MoviesRepositoryImplTest : StringSpec() {

    private val movieRemoteDataSource = mockk<MoviesDataSource>()
    private val memoryMovieDataSource = mockk<MoviesDataSource>()
    private val favoritesMovieDataSource = mockk<FavoritesMovieDataSource>()
    private val movieCache = mockk<MoviesCache>()

    private val testErrorResult = ErrorResult("Ups")
    private val testNetErrorResult = ErrorResult("Timeout")
    private val testMovieData = mockk<Movie>()
    private val testSuccess = Result.Success<List<Movie>>(listOf(testMovieData))

    private lateinit var movieRepository: MoviesRepositoryImpl

    override fun beforeTest(testCase: TestCase) {
        movieRepository = MoviesRepositoryImpl(
                movieRemoteDataSource = movieRemoteDataSource,
                memoryMovieDataSource = memoryMovieDataSource,
                favoritesMovieDataSource = favoritesMovieDataSource,
                movieCache = movieCache
        )

        every { runBlocking { movieCache.saveAll(listOf(testMovieData)) } } returns Unit
    }

    init {
        "fun getPopularMovies() should return Error result when there is no data to show"{
            every { runBlocking { movieRepository.getPopularMovies() } } returns Result.Error(testErrorResult)
            val result = movieRepository.getPopularMovies()
            result.shouldBeInstanceOf<Result.Error>()
        }

        "fun getPopularMovies() represents remote data source result if there is no local data stored"{
            every { runBlocking { memoryMovieDataSource.getPopularMovies() } } returns Result.Error(testErrorResult)
            every { runBlocking { movieRemoteDataSource.getPopularMovies() } } returns Result.Error(testNetErrorResult)
            val result = movieRepository.getPopularMovies()
            result.shouldBeInstanceOf<Result.Error>()
            val error = (result as Result.Error).error
            error shouldBe testNetErrorResult
        }

        "fun getPopularMovies() save remote data source result in case of success"{
            every { runBlocking { memoryMovieDataSource.getPopularMovies() } } returns Result.Error(testErrorResult)
            every { runBlocking { movieRemoteDataSource.getPopularMovies() } } returns testSuccess
            val result = movieRepository.getPopularMovies()
            result.shouldBeInstanceOf<Result.Success<List<Movie>>>()
        }

        "fun getPopularMovies() returns cashed data if exist"{
            every { runBlocking { memoryMovieDataSource.getPopularMovies() } } returns testSuccess

            val result = movieRepository.getPopularMovies()
            result.shouldBeInstanceOf<Result.Success<List<Movie>>>()
        }
    }

}