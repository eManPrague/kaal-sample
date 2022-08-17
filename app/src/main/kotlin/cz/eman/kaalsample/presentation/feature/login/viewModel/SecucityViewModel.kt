package cz.eman.kaalsample.presentation.feature.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.eman.kaal.domain.result.onError
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import cz.eman.kaalsample.presentation.feature.login.mapper.toVo
import cz.eman.kaalsample.presentation.feature.login.model.PswdStrengthVo

class SecucityViewModel(
    private val checkPswdStrength: CheckPswdStrengthUseCase
) : KaalViewModel() {

    private val _pswdStrength = MutableLiveData<PswdStrengthVo>()
    val pswdStrength: LiveData<PswdStrengthVo> = _pswdStrength

    init {
        _pswdStrength.value = PswdStrengthVo(id = null)
    }

    fun onPswdChanged(pswd: String) {
        launch {
            checkPswdStrength(pswd).onSuccess {
                _pswdStrength.value = it.toVo()
            }.onError {
                TODO
            }
        }
    }
}