package cz.eman.kaalsample.presentation.feature.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.domain.feature.usermanagement.model.PassStrength
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPassStrengthUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPassStrengthUseCase.PasswordParams
import cz.eman.kaalsample.presentation.feature.login.extensions.toVo
import cz.eman.kaalsample.presentation.feature.login.model.LoginScreenVo

class PasswordViewModel(
    private val checkPassStrength: CheckPassStrengthUseCase
): KaalViewModel() {

    private val _data = MutableLiveData<LoginScreenVo>()
    val data: LiveData<LoginScreenVo> = _data

    fun onPasswordChange(newPassword: String) {
        launch {

            val passStrength: Result<PassStrength> = checkPassStrength(PasswordParams(newPassword))

            when (passStrength) {
                is Result.Success -> {
                    _data.value = data.value?.copy(
                        passwordText = passStrength.data.toVo()
                    )

                }
            }

        }
    }
}

