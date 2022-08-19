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
import timber.log.Timber

class SecurityViewModel(
    private val checkPswdStrength: CheckPswdStrengthUseCase
) : KaalViewModel(
) {

    private val _pswdStrength = MutableLiveData<PswdStrengthVo>()
    val pswdStrength: LiveData<PswdStrengthVo> = _pswdStrength

    private val _pswdStrengthError = MutableLiveData<String>()
    val pswdStrengthError: LiveData<String> = _pswdStrengthError


    fun onPswdChanged(pswd: String) {
        launch {
            checkPswdStrength(pswd).onSuccess {
                _pswdStrength.value = it.toVo()
            }.onError {
                _pswdStrengthError.value = it.message
                Timber.d("${it.message}", "error result")
            }
        }
    }
}