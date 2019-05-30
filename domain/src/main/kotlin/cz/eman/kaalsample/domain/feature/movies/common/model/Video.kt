package cz.eman.kaalsample.domain.feature.movies.common.model


data class Video(
    var id: String,
    var name: String,
    var key: String? = null,
    var site: String? = null,
    var type: String? = null
)