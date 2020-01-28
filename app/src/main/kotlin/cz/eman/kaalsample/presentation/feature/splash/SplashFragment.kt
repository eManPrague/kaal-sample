package cz.eman.kaalsample.presentation.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.print.PrintHelper.ORIENTATION_LANDSCAPE
import androidx.print.PrintHelper.ORIENTATION_PORTRAIT
import cz.eman.kaal.presentation.fragment.BaseFragment
import cz.eman.kaalsample.R
import cz.eman.kaalsample.presentation.feature.splash.states.SplashStates
import cz.eman.kaalsample.presentation.feature.splash.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *  @author stefan.toth@eman.cz
 */
class SplashFragment : BaseFragment() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        val orientation = activity?.let { resources.configuration.orientation }

        when (orientation) {
            ORIENTATION_PORTRAIT -> view.setBackgroundResource(R.drawable.splash_land)
            ORIENTATION_LANDSCAPE -> view.setBackgroundResource(R.drawable.splash)
        }

        registerEvents()

        return view
    }


    private fun registerEvents() {
        viewModel.viewState.observe(this, Observer {
            when (it) {
                is SplashStates.Begin -> viewModel.initialize()
                is SplashStates.Done -> moveToNextScreen(it.isUserLoggedIn)
            }
        })
    }

    private fun moveToNextScreen(isUserLoggedIn: Boolean) {
        if (isUserLoggedIn) {
            findNavController().navigate(R.id.action_splashFragment_to_dashboardActivity)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_splashLogin)
        }
    }


}