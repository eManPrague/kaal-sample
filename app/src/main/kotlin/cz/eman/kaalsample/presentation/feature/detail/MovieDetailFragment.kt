package cz.eman.kaalsample.presentation.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.eman.kaal.domain.ErrorResult
import cz.eman.kaal.presentation.fragment.BaseFragment
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.infrastructure.file.image.PicassoImageLoader
import cz.eman.kaalsample.presentation.feature.const.Const
import cz.eman.kaalsample.presentation.feature.detail.states.DetailViewStates
import cz.eman.kaalsample.presentation.feature.detail.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.view_error_message.view.*
import kotlinx.android.synthetic.main.view_movie_detail.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.viewModel
import timber.log.Timber

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class MovieDetailFragment : BaseFragment() {

    private val viewModel by viewModel<DetailViewModel>()

    private val imageLoader by inject<PicassoImageLoader>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt(Const.SELECTED_MOVIE_ID, 0) ?: 0
        Timber.i("Open movie detail for movie with id: $movieId / arguments = $arguments")
        registerEvents(movieId)
    }

    private fun registerEvents(movieId: Int) {
        viewModel.viewState.observe(this, Observer {
            showLoading(it.showLoading)
            showError(null)

            when (it) {
                is DetailViewStates.NotInitialized -> viewModel.loadPopularMovies(movieId)
                is DetailViewStates.Loading -> Timber.v("Loading...")
                is DetailViewStates.MovieLoaded -> showLoadedMovie(it.movie)
                is DetailViewStates.LoadingError -> showError(it.error)
            }
        })
    }

    private fun showLoading(show: Boolean) {
        detailProgress.visibility =
                if (show) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
    }

    private fun showLoadedMovie(movie: Movie) {
        detail.visibility = View.VISIBLE

        detail.movieTitle.setText(movie.title)
        detail.movieOverviewSection.setText(movie.overview)
        detail.movieReleaseDate.setText(movie.releaseDate)
        detail.movieScore.setText(movie.voteAverage.toString())

        movie.posterPath?.let { imageLoader.load(url = it, imageView = detail.moviePoster) }

        updateFavoriteStatus(movie)
    }

    private fun updateFavoriteStatus(movie: Movie) {
        updateFavoriteIcon()

        favoriteStatus.setOnClickListener {
            val newState = !viewModel.isThisMovieInFavoriteList
            viewModel.changeFavoriteStatus(movie, newState)
            updateFavoriteIcon()
        }
    }

    private fun updateFavoriteIcon() {
        favoriteStatus.setImageResource(
                if (viewModel.isThisMovieInFavoriteList)
                    R.drawable.ic_favorite_white
                else
                    R.drawable.ic_favorite_border)
    }

    private fun showError(error: ErrorResult?) {
        if (error == null) {
            detailErrorMessage.visibility = View.GONE
            return
        }

        detail.visibility = View.GONE

        Timber.d("Something went wrong: ${error.message}")
        detailErrorMessage.visibility = View.VISIBLE

        val message = error.message?.let {
            getString(R.string.error_message_text_with_additional_info, it)
        } ?: getString(R.string.error_message_text_basic)

        detailErrorMessage.errorBody.setText(message)

        detailErrorMessage.tryAgainButton.setText(R.string.error_message_button_back)

//        detailErrorMessage.tryAgainButton.setOnClickListener {
//            findNavController().popBackStack()
//        }

        detailErrorMessage.tryAgainButton.visibility = View.GONE
    }

}