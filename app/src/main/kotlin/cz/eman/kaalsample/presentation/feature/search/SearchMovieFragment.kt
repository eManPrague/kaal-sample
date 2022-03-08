package cz.eman.kaalsample.presentation.feature.search

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.presentation.fragment.KaalFragment
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.infrastructure.device.isLandscape
import cz.eman.kaalsample.infrastructure.file.image.PicassoImageLoader
import cz.eman.kaalsample.presentation.feature.const.Const.SELECTED_MOVIE_ID
import cz.eman.kaalsample.presentation.feature.search.adapter.SearchMovieAdapter
import cz.eman.kaalsample.presentation.feature.search.states.SearchMovieViewStates
import cz.eman.kaalsample.presentation.feature.search.viewmodel.SearchMovieViewModel
import kotlinx.android.synthetic.main.fragment_popular_movies.errorMessage
import kotlinx.android.synthetic.main.fragment_popular_movies.progress
import kotlinx.android.synthetic.main.fragment_search_movies.*
import kotlinx.android.synthetic.main.view_error_message.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 *  @author stefan.toth@eman.cz
 */
class SearchMovieFragment : KaalFragment() {

    private val viewModel by viewModel<SearchMovieViewModel>()

    private val imageLoader by inject<PicassoImageLoader>()

    private lateinit var moviesAdapter: SearchMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_search_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerEvents()

        moviesAdapter = SearchMovieAdapter(imageLoader = imageLoader, onMovieCallback = { onMovieSelectedEvent(it) })
        searchedMoviesRV.apply {
            layoutManager = activity?.let { GridLayoutManager(it, getSpanCount()) }
            adapter = moviesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // avoid memory leak
        searchedMoviesRV.adapter = null
    }

    private fun registerEvents() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            showLoading(it.showLoading)
            showError(null)
            Timber.d("Popular movies state even observed: ${it.javaClass.simpleName}")
            when (it) {
                is SearchMovieViewStates.NotInitialized -> viewModel.loadMovies()
                is SearchMovieViewStates.Loading -> Timber.v("Loading movies ...")
                is SearchMovieViewStates.MoviesLoaded -> showLoadedMovies(it.movieList)
                is SearchMovieViewStates.LoadingError -> showError(it.error)
            }
        })

        searchMoviesView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                return if (newText != null) {
                    viewModel.loadMovies(newText)
                    true
                } else
                    false
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