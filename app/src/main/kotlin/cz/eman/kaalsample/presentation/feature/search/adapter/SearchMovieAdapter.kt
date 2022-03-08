package cz.eman.kaalsample.presentation.feature.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.movies.common.model.Movie
import cz.eman.kaalsample.infrastructure.file.image.ImageLoader
import kotlinx.android.synthetic.main.view_popular_movie_item.view.*

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class SearchMovieAdapter(private val imageLoader: ImageLoader,
                         private val onMovieCallback: (Movie) -> Unit) : RecyclerView.Adapter<SearchMovieAdapter.PopMoviesViewHolder>() {

    private var movies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_popular_movie_item, parent, false)

        return PopMoviesViewHolder(itemView = view)
    }

    override fun onBindViewHolder(holder: PopMoviesViewHolder, position: Int) {
        holder.bindMovie(movie = movies[position], imageLoader = imageLoader, onMovieCallback = onMovieCallback)
    }

    override fun getItemCount() = movies.size

    public fun addMovies(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    /**
     *
     * @author vsouhrada (vaclav.souhrada@eman.cz)
     */
    class PopMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindMovie(movie: Movie, imageLoader: ImageLoader, onMovieCallback: (Movie) -> Unit) {
            with(itemView) {
                title.text = movie.title
                movie.posterPath?.let { imageLoader.load(url = it, imageView = image) }
                setOnClickListener { onMovieCallback(movie) }
            }
        }
    }

}