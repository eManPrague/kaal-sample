package cz.eman.kaalsample.presentation.feature.favorities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.eman.kaalsample.R
import features.movies.common.model.Movie
import kotlinx.android.synthetic.main.view_favorite_movie_item.view.*

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class FavoriteMoviesAdapter(private val movies: List<Movie>,
                            private val onMovieCallback: (Movie) -> Unit) : RecyclerView.Adapter<FavoriteMoviesAdapter.PopMoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_favorite_movie_item, parent, false)

        return PopMoviesViewHolder(itemView = view)
    }

    override fun onBindViewHolder(holder: PopMoviesViewHolder, position: Int) {
        holder.bindMovie(movie = movies[position], onMovieCallback = onMovieCallback)
    }

    override fun getItemCount() = movies.size

    /**
     *
     * @author vsouhrada (vaclav.souhrada@eman.cz)
     */
    class PopMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindMovie(movie: Movie, onMovieCallback: (Movie) -> Unit) {
            with(itemView) {
                favoriteTitle.text = movie.title
                setOnClickListener { onMovieCallback(movie) }
            }
        }
    }

}