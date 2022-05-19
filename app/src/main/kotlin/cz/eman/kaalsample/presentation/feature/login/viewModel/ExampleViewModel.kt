package cz.eman.kaalsample.presentation.feature.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.presentation.viewmodel.launch
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.PswdStrengthUseCase
import cz.eman.kaalsample.presentation.feature.login.extension.toVo
import cz.eman.kaalsample.presentation.feature.login.model.LongScreenVo

class ExampleViewModel(
    private val pswdStrength: PswdStrengthUseCase
) : ViewModel() {

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int> = _state


    private val _data = MutableLiveData<LongScreenVo>()
    val data: LiveData<LongScreenVo> = _data

    fun onPswdChanged(newPswd: String) {
        launch {
            val result = pswdStrength(newPswd)
            when {
                result is Result.Success -> {
                    _data.value = data.value?.copy(
                        passwordStrengthText = result.data.toVo()
                    )
                }
                else -> TODO("show error message")
            }
        }
    }


}