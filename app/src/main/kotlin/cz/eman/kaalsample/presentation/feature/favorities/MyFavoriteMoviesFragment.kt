package cz.eman.kaalsample.presentation.feature.favorities

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.presentation.fragment.BaseFragment
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.presentation.feature.const.Const
import cz.eman.kaalsample.presentation.feature.favorities.adapter.FavoriteMoviesAdapter
import cz.eman.kaalsample.presentation.feature.favorities.states.FavoriteMoviesViewStates
import cz.eman.kaalsample.presentation.feature.favorities.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_my_favorite_movies.*
import org.koin.androidx.viewmodel.ext.viewModel
import timber.log.Timber

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class MyFavoriteMoviesFragment : BaseFragment() {

    private val viewModel by viewModel<FavoritesViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_my_favorite_movies, container, false)
        registerEvents()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Note that this recyclerView is an old one
        // and different instance from the one in onViewCreated.
        recycler.adapter = null
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkIfValidData()
    }

    private fun registerEvents() {
        viewModel.viewState.observe(this, Observer {
            favoritesText.visibility = if (it.isThereContentToShow) View.GONE else View.VISIBLE

            when (it) {
                is FavoriteMoviesViewStates.NotInitialized -> viewModel.loadFavoriteMovies()
                is FavoriteMoviesViewStates.Loading -> Timber.v("Loading...")
                is FavoriteMoviesViewStates.MoviesLoaded -> showLoadedMovies(it.movieList)
                is FavoriteMoviesViewStates.LoadingError -> showError(it.error)
            }
        })
    }

    private fun showLoadedMovies(movies: List<Movie>) {
        Timber.v("The list size in favorites is: ${movies.size}")
        recycler.apply {
            layoutManager = activity?.let { LinearLayoutManager(context) }
            adapter = FavoriteMoviesAdapter(movies = movies, onMovieCallback = { onMovieSelectedEvent(it) })
        }
    }

    private fun showError(error: ErrorResult) {
        Toast.makeText(context, "${error.message}", Toast.LENGTH_LONG).show()
    }

    private var mLastClickTime = 0L

    private fun onMovieSelectedEvent(movie: Movie) {
        Timber.i("Open movie detail for movie with id: ${movie.id}")

        // need to prevent double click :-(
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        viewModel.invalidate()

        val bundle = bundleOf(
                Const.SELECTED_MOVIE_ID to movie.id
        )

        findNavController().navigate(R.id.action_favoriteMoviesFragment_to_detailMovieActivity, bundle)
    }

}