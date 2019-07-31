package cz.eman.kaalsample.domain.feature.movies

import cz.eman.kaal.domain.ErrorResult
import cz.eman.kaal.domain.Result
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository
import cz.eman.kaalsample.domain.feature.movies.popular.usecase.GetPopularMoviesUseCase
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking

/**
 *  @author stefan.toth@eman.cz
 */
class GetPopularMoviesUseCaseTest : StringSpec({

    "Get popular moves use case should call MoviesRepository"{
        val repo = mockk<MoviesRepository>()
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(repo)
        every { runBlocking { repo.getPopularMovies() } } returns Result.Success<List<Movie>>(mockk())
        getPopularMoviesUseCase(Unit)
        coVerify { repo.getPopularMovies() }
    }

    "In case of Success The get popular moivies use case result value is equal as fun MovieRepository.getPopularMovies()"{
        val repo = mockk<MoviesRepository>()
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(repo)
        val testMovie = mockk<Movie>()
        every { runBlocking { repo.getPopularMovies() } } returns Result.Success<List<Movie>>(listOf(testMovie))
        val result = getPopularMoviesUseCase(Unit)
        result.shouldBeInstanceOf<Result.Success<List<Movie>>>()
        val data = (result as Result.Success).data
        data shouldBe listOf(testMovie)
    }

    "In case of Error The get popular moivies use case result value is equal as fun MovieRepository.getPopularMovies()"{
        val repo = mockk<MoviesRepository>()
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(repo)
        val errorResult = ErrorResult("Ups...")
        every { runBlocking { repo.getPopularMovies() } } returns Result.Error(errorResult)
        val result = getPopularMoviesUseCase(Unit)
        result.shouldBeInstanceOf<Result.Error>()
        val error = (result as Result.Error).error
        error shouldBe errorResult
    }
})