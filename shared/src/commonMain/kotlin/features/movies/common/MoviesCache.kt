package features.movies.common

import features.movies.common.model.Movie

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
interface MoviesCache {

    suspend fun save(movie: Movie)

    suspend fun remove(movie: Movie)

    suspend fun saveAll(movies: List<Movie>)

    suspend fun getAll(): List<Movie>

    suspend fun getMovieById(movieId: Int): Movie?

    suspend fun search(query: String): List<Movie>

    suspend fun isEmpty(): Boolean

    suspend fun clear()
}