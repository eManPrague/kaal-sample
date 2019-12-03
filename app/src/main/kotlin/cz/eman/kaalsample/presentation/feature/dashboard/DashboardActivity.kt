package cz.eman.kaalsample.presentation.feature.dashboard

import android.os.Bundle
import androidx.navigation.findNavController
import cz.eman.kaal.presentation.activity.BaseActivity
import cz.eman.kaalsample.R

/**
 *  @author stefan.toth@eman.cz
 */
class DashboardActivity : BaseActivity(R.layout.activity_dashboard) {

    override fun onSupportNavigateUp() = findNavController(R.id.navHostFragment).navigateUp()
}