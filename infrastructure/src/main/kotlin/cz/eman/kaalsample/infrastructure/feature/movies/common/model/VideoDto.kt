package cz.eman.kaalsample.infrastructure.feature.movies.common.model


data class VideoDto(
    var id: String,
    var name: String,
    var key: String? = null,
    var site: String? = null,
    var type: String? = null
)