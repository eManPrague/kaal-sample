package cz.eman.kaalsample.presentation.feature.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.domain.feature.usermanagement.model.User
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.AuthorizeUserUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPsswdStrengthUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.RegisterUserUseCase
import cz.eman.kaalsample.presentation.feature.login.model.PswdStateVo
import cz.eman.kaalsample.presentation.feature.login.model.toPswdState
import cz.eman.kaalsample.presentation.feature.login.states.LoginStates
import cz.eman.logger.logDebug
import cz.eman.logger.logVerbose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  @author stefan.toth@eman.cz
 */
class LoginViewModel(
    private val authoriseUser: AuthorizeUserUseCase,
    private val registerUser: RegisterUserUseCase,
    private val checkPsswdStrength: CheckPsswdStrengthUseCase
) : KaalViewModel() {

    // fixme - use SingleLiveData instead of MutableLiveData
    val loginStates = MutableLiveData<LoginStates>()

    private val _passwordStrength = MutableLiveData<PswdStateVo>()
    val passwordStrength: LiveData<PswdStateVo> = _passwordStrength

    init {
        loginStates.value = LoginStates.IdleState
    }

    fun setToIdle() {
        loginStates.value = LoginStates.IdleState
    }

    var loginUseCase = true


    fun processUser(userName: String, password: String) {
        val user = User(userName, password)
        logVerbose("process user: $userName with usecase login = $loginUseCase")
        if (loginUseCase) {
            loginUser(user)
        } else {
            registerUser(user)
        }
    }

    fun validatePassword(pswd: String) {
        launch {
            val result = checkPsswdStrength(pswd)
            _passwordStrength.value = result.toPswdState()
        }
    }

    private fun loginUser(user: User) {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { authoriseUser(AuthorizeUserUseCase.Params(user)) }
            logDebug(" authorisation result: $result")
            when (result) {
                is Result.Success -> loginStates.postValue(LoginStates.LoginDone)
                is Result.Error -> loginStates.postValue(LoginStates.ErrorResult(result.error.message))
            }
        }
    }

    private fun registerUser(user: User) {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { registerUser(RegisterUserUseCase.Params(user)) }
            logDebug(" registration result: $result")
            when (result) {
                is Result.Success -> loginStates.postValue(LoginStates.RegistrationDone)
                is Result.Error -> loginStates.postValue(LoginStates.ErrorResult(result.error.message))
            }
        }
    }
}