package cz.eman.kaalsample.presentation.feature.login.mapper


import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.presentation.feature.login.model.PswdStrengthVo

fun PswdStrength.toVo() : PswdStrengthVo {
    return when (this) {
        PswdStrength.STRONG -> PswdStrengthVo(R.string.pswd_strong, R.color.green, true)
        PswdStrength.MEDIUM -> PswdStrengthVo(R.string.pswd_medium, R.color.orange, true)
        PswdStrength.WEAK -> PswdStrengthVo(R.string.pswd_weak, R.color.orange, true)
        PswdStrength.SHORT -> PswdStrengthVo(R.string.pswd_short, R.color.red, false)
        PswdStrength.INVALID -> PswdStrengthVo(R.string.pswd_invalid, R.color.red, false)
    }
}