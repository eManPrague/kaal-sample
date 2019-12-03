package cz.eman.kaalsample.presentation.feature.detail

import android.os.Bundle
import androidx.navigation.findNavController
import cz.eman.kaal.presentation.activity.BaseActivity
import cz.eman.kaalsample.R
import timber.log.Timber

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class DetailActivity : BaseActivity(R.layout.activity_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // In this way we could pass the arguments to fragment in start destination
        Timber.d("Intent extras: ${intent.extras}")
        findNavController(R.id.detailNavHostFragment).setGraph(
            R.navigation.nav_graph_movie_detail,
            intent.extras
        )
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navHostFragment).navigateUp()
}