package cz.eman.kaalsample.domain.feature.movies.popular.usecase

import cz.eman.kaal.domain.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository

/**
 * By this use-case you can get current a list of the most popular movies in an asynchronous way.
 *
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[UseCase]
 */
class GetPopularMoviesUseCase(private val moviesRepository: MoviesRepository) : UseCaseResult<List<Movie>, Unit>() {

    override suspend fun doWork(params: Unit): Result<List<Movie>> {
        return moviesRepository.getPopularMovies()
        //return Result.Success(listOf(movie))
    }

    private val movie = Movie(
            adult = false,
            backdropPath = "/v4yVTbbl8dE1UP2dWu5CLyaXOku.jpg",
            id = 420817,
            originalTitle = "Aladdin",
            originalLanguage = "en",
            releaseDate = "2019-05-22",
            posterPath = "/3iYQTLGoy7QnjcUYRJy4YrAgGvp.jpg",
            popularity = 630.556,
            title = "Aladdin",
            voteAverage = 7.2,
            voteCount = 538,
            overview = "A kindhearted street urchin named Aladdin embarks on a magical adventure after finding a lamp that releases a wisecracking genie while a power-hungry Grand Vizier vies for the same lamp that has the power to make their deepest wishes come true.",
            movieDetail = null
    )
}