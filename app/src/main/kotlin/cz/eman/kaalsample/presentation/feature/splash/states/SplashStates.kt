package cz.eman.kaalsample.presentation.feature.splash.states

/**
 *  @author stefan.toth@eman.cz
 */
sealed class SplashStates {
    object Begin : SplashStates()
    class Done(val isUserLoggedIn: Boolean) : SplashStates()
}