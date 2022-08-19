package cz.eman.kaalsample.presentation.feature.login.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.eman.kaal.domain.result.onError
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPasswordStrengthUseCase
import cz.eman.kaalsample.presentation.feature.login.mapper.toPasswordStrengthVo
import cz.eman.kaalsample.presentation.feature.login.model.PasswordStrengthVo
import timber.log.Timber

class SecurityViewModel(
    private val checkPasswordStrength: CheckPasswordStrengthUseCase
) : KaalViewModel() {



    private val _passwordStrength : MutableLiveData<PasswordStrengthVo> by lazy {
        MutableLiveData<PasswordStrengthVo>(CheckPasswordStrengthUseCase.PasswordState.EMPTY.toPasswordStrengthVo())
    }

    val passwordStrength = _passwordStrength as LiveData<PasswordStrengthVo>

    fun onPasswordChanged(password: String) {
        launch {
            checkPasswordStrength(password).onSuccess {
                _passwordStrength.value = it.toPasswordStrengthVo()
            }.onError {
                _passwordStrength.value = PasswordStrengthVo(null, null, false)
                Timber.e("Error checking password")
            }
        }
    }
}