package cz.eman.kaalsample.infrastructure.feature.movies.favorite.db.dao

import androidx.room.Dao
import androidx.room.Query
import cz.eman.kaalsample.infrastructure.feature.movies.favorite.db.entity.FavoriteMovieEntity
import cz.eman.kaalsample.data.common.room.BaseDao

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
@Dao
interface FavoriteMovieDao : BaseDao<FavoriteMovieEntity> {

    @Query("SELECT * FROM favorite_movie")
    suspend fun loadFavoriteMovies(): List<FavoriteMovieEntity>

    @Query("SELECT * FROM favorite_movie WHERE id = :movieId")
    suspend fun loadMovieById(movieId: Int): FavoriteMovieEntity?

    @Query("SELECT * FROM favorite_movie WHERE title LIKE :query")
    suspend fun search(query: String): List<FavoriteMovieEntity>

    @Query("DELETE FROM favorite_movie")
    suspend fun clear()

    @Query("SELECT COUNT(id) FROM favorite_movie")
    fun loadCountOfMovies(): Int

}