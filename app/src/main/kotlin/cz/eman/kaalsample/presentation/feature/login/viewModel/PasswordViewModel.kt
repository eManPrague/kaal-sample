package cz.eman.kaalsample.presentation.feature.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.domain.feature.usermanagement.model.Password
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import cz.eman.kaalsample.presentation.feature.login.model.PasswordVO
import cz.eman.kaalsample.presentation.feature.login.model.toVO

class PasswordViewModel(
    private val checkPswdStrength: CheckPswdStrengthUseCase
) : KaalViewModel() {

    private val _data: MutableLiveData<PasswordVO> = MutableLiveData(PasswordVO())
    val data: LiveData<PasswordVO> = _data


    fun onPswdChanged(password: String) {
        launch {
            val pswdStrength = checkPswdStrength(Password(password))
            _data.value = pswdStrength.toVO()
        }
    }
}