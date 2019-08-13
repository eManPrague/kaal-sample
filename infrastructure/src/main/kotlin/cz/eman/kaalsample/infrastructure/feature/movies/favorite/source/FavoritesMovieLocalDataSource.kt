package cz.eman.kaalsample.infrastructure.feature.movies.favorite.source

import cz.eman.kaalsample.infrastructure.feature.movies.favorite.db.dao.FavoriteMovieDao
import cz.eman.kaalsample.infrastructure.feature.movies.favorite.mapper.FavoriteMovieMapper
import features.movies.common.model.Movie
import features.movies.favorite.source.FavoritesMovieDataSource

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class FavoritesMovieLocalDataSource(private val favoriteMovieDao: FavoriteMovieDao) :
        FavoritesMovieDataSource {

    override suspend fun save(movie: Movie) {
        favoriteMovieDao.insert(FavoriteMovieMapper.mapToDbEntity(movie))
    }

    override suspend fun remove(movie: Movie) {
        favoriteMovieDao.delete(FavoriteMovieMapper.mapToDbEntity(movie))
    }

    override suspend fun saveAll(movies: List<Movie>) {
        val dbEntities = movies.map { FavoriteMovieMapper.mapToDbEntity(it) }
        favoriteMovieDao.insert(*dbEntities.toTypedArray())
    }

    override suspend fun getAll(): List<Movie> =
        favoriteMovieDao.loadFavoriteMovies().map { FavoriteMovieMapper.mapToMovie(it) }

    override suspend fun getMovieById(movieId: Int) : Movie? {
        val movie = favoriteMovieDao.loadMovieById(movieId)
        return if (movie != null) {
            FavoriteMovieMapper.mapToMovie(movie)
        } else {
            null
        }
    }

    override suspend fun search(query: String): List<Movie> {
        return favoriteMovieDao.search(query).map { FavoriteMovieMapper.mapToMovie(it) }
    }

    override suspend fun isEmpty() = favoriteMovieDao.loadCountOfMovies() <= 0

    override suspend fun clear() = favoriteMovieDao.clear()

}