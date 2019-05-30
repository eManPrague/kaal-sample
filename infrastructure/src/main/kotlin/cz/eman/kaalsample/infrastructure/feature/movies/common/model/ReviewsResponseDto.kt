package cz.eman.kaalsample.infrastructure.feature.movies.common.model

import cz.eman.kaalsample.domain.feature.movies.common.model.Review

class ReviewsResponseDto {
    var results: List<Review> = emptyList()
}