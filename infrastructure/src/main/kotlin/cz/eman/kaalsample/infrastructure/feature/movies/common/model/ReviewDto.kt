package cz.eman.kaalsample.infrastructure.feature.movies.common.model

data class ReviewDto(
        var id: String,
        var author: String,
        var content: String? = null
)