package features.movies.common.model


data class MovieDetail(
    var adult: Boolean = false,
    var budget: Int? = null,
    var genres: List<Genre> = emptyList(),
    var videos: List<Video> = emptyList(),
    var reviews: List<Review> = emptyList(),
    var homepage: String? = null,
    var id: Int = -1,
    var imdbId: String? = null,
    var popularity: Double = 0.0,
    var revenue: Int? = null,
    var runtime: Int? = null,
    var tagline: String? = null,
    var video: Boolean = false,
    var voteAverage: Double = 0.0,
    var voteCount: Int = 0,
    var title: String,
    var posterPath: String,
    var originalLanguage: String,
    var originalTitle: String,
    var backdropPath: String,
    var overview: String,
    var releaseDate: String
)