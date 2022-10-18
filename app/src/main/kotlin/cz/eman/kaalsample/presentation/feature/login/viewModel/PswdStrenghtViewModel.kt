package cz.eman.kaalsample.presentation.feature.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.eman.kaal.domain.result.onError
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.domain.feature.usermanagement.model.EncriptedPswd
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import cz.eman.kaalsample.presentation.feature.login.mapper.toVo
import cz.eman.kaalsample.presentation.feature.login.model.RegisterUserVo

class PswdStrenghtViewModel(
    private val checkPswdStrength: CheckPswdStrengthUseCase
) : KaalViewModel() {

    private val _data = MutableLiveData<RegisterUserVo>()
    val data: LiveData<RegisterUserVo> = _data

    val state = MutableLiveData<String>()

    fun onPswdChanged(pswd: String) {
        launch {
            checkPswdStrength(EncriptedPswd(pswd)).onSuccess {
                _data.value = data.value?.copy(
                    error = it.toVo()
                )
            }.onError {
                state.value = "ERROR"
            }
        }
    }

}