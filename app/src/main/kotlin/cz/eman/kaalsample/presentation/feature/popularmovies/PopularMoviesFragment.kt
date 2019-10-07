package cz.eman.kaalsample.presentation.feature.popularmovies

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.presentation.fragment.BaseFragment
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.infrastructure.device.isLandscape
import cz.eman.kaalsample.infrastructure.file.image.PicassoImageLoader
import cz.eman.kaalsample.presentation.feature.const.Const.SELECTED_MOVIE_ID
import cz.eman.kaalsample.presentation.feature.popularmovies.adapter.PopularMoviesAdapter
import cz.eman.kaalsample.presentation.feature.popularmovies.states.PopularMoviesViewStates
import cz.eman.kaalsample.presentation.feature.popularmovies.viewmodel.PopularMoviesViewModel
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import kotlinx.android.synthetic.main.view_error_message.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.viewModel
import timber.log.Timber

/**
 *  @author stefan.toth@eman.cz
 */
class PopularMoviesFragment : BaseFragment() {

    private val viewModel by viewModel<PopularMoviesViewModel>()

    private val imageLoader by inject<PicassoImageLoader>()

    private lateinit var moviesAdapter: PopularMoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_popular_movies, container, false)

        registerEvents()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = PopularMoviesAdapter(imageLoader = imageLoader, onMovieCallback = { onMovieSelectedEvent(it) })
        recycler.apply {
            layoutManager = activity?.let { GridLayoutManager(it, getSpanCount()) }
            adapter = moviesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // avoid memory leak
        recycler.adapter = null
    }

    private fun registerEvents() {
        viewModel.viewState.observe(this, Observer {
            showLoading(it.showLoading)
            showError(null)
            Timber.d("Popular movies state even observed: ${it.javaClass.simpleName}")
            when (it) {
                is PopularMoviesViewStates.NotInitialized -> viewModel.loadPopularMovies()
                is PopularMoviesViewStates.Loading -> Timber.v("Loading movies ...")
                is PopularMoviesViewStates.MoviesLoaded -> showLoadedMovies(it.movieList)
                is PopularMoviesViewStates.LoadingError -> showError(it.error)
            }
        })
    }

    private fun showLoading(show: Boolean) {
        progress.visibility =
                if (show) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
    }

    private fun showLoadedMovies(movieList: List<Movie>) {
        Timber.d("All movies are loaded. List size = ${movieList.size}")
        moviesAdapter.addMovies(movieList)
    }

    private fun showError(error: ErrorResult?) {
        if (error == null) {
            errorMessage.visibility = View.GONE
            return
        }

        Timber.d("Something went wrong: ${error.message}")
        errorMessage.visibility = View.VISIBLE

        val message = error.message?.let {
            getString(R.string.error_message_text_with_additional_info, it)
        } ?: getString(R.string.error_message_text_basic)

        errorMessage.errorBody.setText(message)

        errorMessage.tryAgainButton.setOnClickListener {
            viewModel.reset()
        }
    }

    private var mLastClickTime = 0L

    private fun onMovieSelectedEvent(movie: Movie) {
        Timber.i("Open movie detail for movie with id: ${movie.id}")

        // need to prevent double click :-(
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();


        val bundle = bundleOf(SELECTED_MOVIE_ID to movie.id)
        findNavController().navigate(R.id.action_popularMoviesFragment_to_detailMovieActivity, bundle)
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkIfValidData()
    }

    private fun getSpanCount(): Int {
        val context = activity
        return if (context != null && context.isLandscape()) {
            3
        } else {
            2
        }
    }

}