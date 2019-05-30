package cz.eman.kaalsample.infrastructure.feature.movies.common.apiservice

import cz.eman.kaalsample.infrastructure.feature.movies.common.model.MovieDetailDto
import cz.eman.kaalsample.infrastructure.feature.movies.common.model.MoviesWrapperDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
interface MovieApiService {

    @GET("movie/popular") ///movie/now_playing
    fun getPopularMovies(): Call<MoviesWrapperDto>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String): Call<MoviesWrapperDto>


    @GET("movie/{id}?append_to_response=videos,reviews")
    suspend fun getMovieDetails(@Path("id") movieId: Int): Call<MovieDetailDto>

}