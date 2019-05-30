package cz.eman.kaalsample.infrastructure.feature.movies.common.model

/**
 * @author vsouhrada
 * @version 1.0.0
 * @since 1.0.0
 */
data class MoviesWrapperDto(
        var page: Int = 0,
        var results: List<MovieDto> = emptyList(),
        var total_pages: Int = 0,
        var total_results: Int = 0
)