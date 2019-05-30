package cz.eman.kaalsample.infrastructure.feature.movies.common.model

import com.google.gson.annotations.SerializedName

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
data class MovieImageDto(
        @SerializedName("width")
        val width: Int,

        @SerializedName("height")
        val height: Int,

        @SerializedName("file_path")
        val filePath: String,

        @SerializedName("iso_639_1")
        val iso6391: String,

        @SerializedName("aspect_ratio")
        val aspectRatio: Float,

        @SerializedName("vote_average")
        val voteAverage: Float,

        @SerializedName("vote_count")
        val voteCount: Int
)
