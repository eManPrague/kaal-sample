package cz.eman.kaalsample.infrastructure.feature.movies.favorite.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_movie")
data class FavoriteMovieEntity(

    @PrimaryKey
    var id: Int = -1,
    var voteCount: Int = 0,
    var voteAverage: Double = 0.0,
    var adult: Boolean = false,
    var popularity: Double = 0.0,
    var title: String,
    var posterPath: String? = null,
    var originalLanguage: String,
    var originalTitle: String,
    var backdropPath: String? = null,
    var releaseDate: String? = null,
    var overview: String
)