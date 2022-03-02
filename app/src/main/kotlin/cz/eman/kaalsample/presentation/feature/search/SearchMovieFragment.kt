package cz.eman.kaalsample.presentation.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.eman.kaal.presentation.fragment.KaalFragment
import cz.eman.kaalsample.R

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[KaalFragment]
 */
class SearchMovieFragment : KaalFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_search_movies, container, false)
    }

}