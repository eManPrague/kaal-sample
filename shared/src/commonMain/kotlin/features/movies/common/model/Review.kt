package features.movies.common.model

data class Review(
        var id: String,
        var author: String,
        var content: String? = null
)