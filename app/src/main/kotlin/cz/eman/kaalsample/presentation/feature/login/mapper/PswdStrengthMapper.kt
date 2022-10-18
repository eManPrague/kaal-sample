package cz.eman.kaalsample.presentation.feature.login.mapper

import androidx.annotation.StringRes
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength

@StringRes
fun PswdStrength.toVo() : Int {
    when(this){
        PswdStrength.MEDIUM -> R.string.pswd_medium
    }

}