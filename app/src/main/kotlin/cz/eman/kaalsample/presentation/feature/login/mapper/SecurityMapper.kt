package cz.eman.kaalsample.presentation.feature.login.mapper

import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.presentation.feature.login.model.PswdStrengthVo

fun PswdStrength.toVo(): PswdStrengthVo = when (this) {
    PswdStrength.OK -> PswdStrengthVo(id = R.string.pswd_strong, colorRes = R.color.pswd_ok)
    PswdStrength.MEDIUM -> PswdStrengthVo(id = R.string.pswd_medium)
    PswdStrength.LOW -> PswdStrengthVo(id = R.string.pswd_low)
    PswdStrength.INVALID -> PswdStrengthVo(id = R.string.pswd_invalid)
    else -> PswdStrengthVo(id = R.string.pswd_fail)
}