package cz.eman.kaalsample.presentation.feature.splash.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaalsample.presentation.feature.splash.states.SplashStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  @author stefan.toth@eman.cz
 */
class SplashViewModel : KaalViewModel() {

    val viewState = MutableLiveData<SplashStates>()

    init {
        viewState.value = SplashStates.Begin
    }

    fun initialize() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2500)
            viewState.postValue(SplashStates.Done(false))
        }
    }
}