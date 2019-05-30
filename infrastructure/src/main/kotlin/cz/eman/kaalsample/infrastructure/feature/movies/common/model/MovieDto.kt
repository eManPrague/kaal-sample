package cz.eman.kaalsample.infrastructure.feature.movies.common.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
data class MovieDto(
        @SerializedName("id")
        var id: Int = -1,

        @SerializedName("vote_count")
        var voteCount: Int = 0,

        @SerializedName("vote_average")
        var voteAverage: Double = 0.0,

        @SerializedName("adult")
        var adult: Boolean = false,

        @SerializedName("popularity")
        var popularity: Double = 0.0,

        @SerializedName("title")
        var title: String,

        @SerializedName("poster_path")
        var posterPath: String? = null,

        @SerializedName("original_language")
        var originalLanguage: String,

        @SerializedName("original_title")
        var originalTitle: String,

        @SerializedName("backdrop_path")
        var backdropPath: String? = null,

        @SerializedName("release_date")
        var releaseDate: String,

        @SerializedName("overview")
        var overview: String?,

        @SerializedName("video")
        var video: Boolean = false
)