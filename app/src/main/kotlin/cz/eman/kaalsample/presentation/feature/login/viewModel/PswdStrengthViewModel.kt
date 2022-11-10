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
import cz.eman.kaalsample.presentation.feature.login.model.PswdStrengthVo
import cz.eman.kaalsample.R

class PswdStrengthViewModel(
    private val checkPswdStrength: CheckPswdStrengthUseCase
) : KaalViewModel() {

//    private val _data = MutableLiveData<RegisterUserVo>()
//    val data: LiveData<RegisterUserVo> = _data

    private val _pswdStrength = MutableLiveData<PswdStrengthVo>()
    val data: LiveData<PswdStrengthVo> = _pswdStrength

    init {
        _pswdStrength.value = PswdStrengthVo(R.string.pswd_invalid, R.color.red, false )
    }

    fun onPswdChanged(pswd: String) {
        launch {
            checkPswdStrength(EncriptedPswd(pswd)).onSuccess {
                _pswdStrength.value = it.toVo()
            }.onError {
                _pswdStrength.value = PswdStrengthVo(R.string.pswd_invalid, R.color.red, false )
            }
        }
    }

}