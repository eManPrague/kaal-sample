package cz.eman.kaalsample.presentation.feature.dashboard

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import cz.eman.kaal.presentation.activity.BaseActivity
import cz.eman.kaalsample.R
import kotlinx.android.synthetic.main.activity_dashboard.*

/**
 *  @author stefan.toth@eman.cz
 */
class DashboardActivity : BaseActivity(R.layout.activity_dashboard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NavigationUI.setupWithNavController(bottomNav, findNavController(R.id.navHostFragment))
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navHostFragment).navigateUp()
}