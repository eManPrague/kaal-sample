package cz.eman.kaalsample.infrastructure.feature.movies.common.model

import features.movies.common.model.Review

class ReviewsResponseDto {
    var results: List<Review> = emptyList()
}