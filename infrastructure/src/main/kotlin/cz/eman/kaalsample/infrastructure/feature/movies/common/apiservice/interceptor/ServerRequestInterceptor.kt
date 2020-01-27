package cz.eman.kaalsample.infrastructure.feature.movies.common.apiservice.interceptor

import cz.eman.kaalsample.infrastructure.feature.movies.common.apiservice.MOVIE_DB_API_KEY
import cz.eman.kaalsample.infrastructure.feature.movies.common.apiservice.MOVIE_DB_API_KEY_TEXT
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Roman Holomek (roman.holomek@eman.cz)
 * @see[Interceptor]
 */
object ServerRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val url = with(original.url.newBuilder()) {
            addQueryParameter(
                MOVIE_DB_API_KEY_TEXT,
                MOVIE_DB_API_KEY
            )
            build()
        }

        val requestBuilder = original.newBuilder().url(url)

        return chain.proceed(requestBuilder.build())
    }
}