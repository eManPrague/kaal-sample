package cz.eman.kaalsample.presentation.feature.login.states

/**
 *  @author stefan.toth@eman.cz
 */
sealed class LoginStates {

    object IdleState : LoginStates()
    object LoginDone : LoginStates()
    object RegistrationDone : LoginStates()
    class ErrorResult(val message: String?) : LoginStates()

}