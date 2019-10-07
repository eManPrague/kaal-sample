package cz.eman.kaalsample.data.feature.movies.common.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.movies.common.MoviesCache
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.repository.MoviesRepository
import cz.eman.kaalsample.domain.feature.movies.common.source.MoviesDataSource
import cz.eman.kaalsample.domain.feature.movies.favorite.source.FavoritesMovieDataSource

/**
 *
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[MoviesRepository]
 */
class MoviesRepositoryImpl(
        private val movieRemoteDataSource: MoviesDataSource,
        private val memoryMovieDataSource: MoviesDataSource,
        private val favoritesMovieDataSource: FavoritesMovieDataSource,
        private val movieCache: MoviesCache
) : MoviesRepository {

    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return when (val memoryDataResult = memoryMovieDataSource.getPopularMovies()) {
            is Result.Success -> memoryDataResult
            else -> {
                val result = movieRemoteDataSource.getPopularMovies()
                if (result is Result.Success) {
                    movieCache.saveAll(result.data)
                }
                result
            }
        }
    }

    override suspend fun getFavoriteMovies(): Result<List<Movie>> {
        return Result.Success(favoritesMovieDataSource.getAll())
    }

    override suspend fun checkFavoriteStatus(id: Int): Boolean {
        return favoritesMovieDataSource.getMovieById(id)?.let { true } ?: false
    }

    override suspend fun changeFavoriteStatus(movie: Movie, newFavoriteStatus: Boolean) {
        val wasFavorite = checkFavoriteStatus(movie.id)
        when {
            wasFavorite && !newFavoriteStatus -> favoritesMovieDataSource.remove(movie)
            !wasFavorite && newFavoriteStatus -> favoritesMovieDataSource.save(movie)
            // else nothing to do
        }
    }

    override suspend fun getMovieById(id: Int): Result<Movie> {
        return memoryMovieDataSource.getMovieById(id)
    }

}