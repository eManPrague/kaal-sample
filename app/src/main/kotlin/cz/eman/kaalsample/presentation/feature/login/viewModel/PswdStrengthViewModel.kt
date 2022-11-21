package cz.eman.kaalsample.presentation.feature.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.eman.kaal.domain.result.onError
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.EncriptedPswd
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import cz.eman.kaalsample.presentation.feature.login.mapper.toVo
import cz.eman.kaalsample.presentation.feature.login.model.PswdStrengthVo

class PswdStrengthViewModel(
    private val checkPswdStrength: CheckPswdStrengthUseCase
) : KaalViewModel() {

    private val _data = MutableLiveData<PswdStrengthVo>()
    val data: LiveData<PswdStrengthVo> = _data

    fun onPswdChanged(pswd: String) {
        launch {
            checkPswdStrength(EncriptedPswd(pswd)).onSuccess {
                _data.value = it.toVo()
            }.onError {
                _data.value = PswdStrengthVo(false, R.color.red, R.string.password_error )
            }
        }
    }

}