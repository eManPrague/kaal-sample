package cz.eman.kaalsample.infrastructure.feature.movies.common.cache

import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.domain.feature.movies.common.MoviesCache

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[MoviesCache]
 */
class MemoryMovieCache : MoviesCache {

    private val cache: HashMap<Int, Movie> = LinkedHashMap()

    override suspend fun save(movie: Movie) {
        cache[movie.id] = movie
    }

    override suspend fun remove(movie: Movie) {
        cache.remove(movie.id)
    }

    override suspend fun saveAll(movies: List<Movie>) {
        if (movies.isNotEmpty()) {
            movies.forEach { movie -> save(movie) }
        }
    }

    override suspend fun getAll() = cache.values.toList()

    override suspend fun getMovieById(movieId: Int) = cache[movieId]

    override suspend fun search(query: String): List<Movie> {
        return cache.values.filter { it.title.contains(query) || it.originalTitle.contains(query) }
    }

    override suspend fun isEmpty() = cache.isEmpty()

    override suspend fun clear() {
        cache.clear()
    }

}