package cz.eman.kaalsample.presentation.feature.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import cz.eman.kaalsample.presentation.feature.login.mapper.toLoginModelVo
import cz.eman.kaalsample.presentation.feature.login.model.LoginModelVo
import cz.eman.kaalsample.presentation.feature.login.states.LoginStates

class SecurityViewModel(
    private val checkPswdStrength: CheckPswdStrengthUseCase
) : KaalViewModel() {

    private val _state = MutableLiveData<LoginStates>()
    val state: LiveData<LoginStates> = _state

    private val _data = MutableLiveData<LoginModelVo>()
    val data: LiveData<LoginModelVo> = _data

    fun onRegPswdChanged(pswd: String) {
        launch {
            checkPswdStrength(pswd).onSuccess {
                _data.value = it.toLoginModelVo(data.value)
            }
        }
    }
}