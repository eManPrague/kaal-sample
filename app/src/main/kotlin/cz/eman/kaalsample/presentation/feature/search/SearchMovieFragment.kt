package cz.eman.kaalsample.presentation.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.eman.kaal.presentation.fragment.BaseFragment
import cz.eman.kaalsample.R

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[BaseFragment]
 */
class SearchMovieFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_search_movies, container, false)
    }

}