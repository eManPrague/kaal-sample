package cz.eman.kaalsample.presentation.main

import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.findNavController
import cz.eman.kaal.presentation.activity.KaalActivity
import cz.eman.kaalsample.R

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class MainActivity : KaalActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navHostFragment).navigateUp()

}