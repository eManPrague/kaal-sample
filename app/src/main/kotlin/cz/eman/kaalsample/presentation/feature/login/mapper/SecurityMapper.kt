package cz.eman.kaalsample.presentation.feature.login.mapper

import androidx.annotation.StringRes
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrngth
import cz.eman.kaalsample.presentation.feature.login.model.LoginModelVo

@StringRes
fun PswdStrngth.toPswdError() = when (this) {
    PswdStrngth.LOW -> R.string.login_screen_login
    else -> R.string.app_name
}

fun PswdStrngth.toLoginModelVo(data: LoginModelVo?): LoginModelVo {

}
