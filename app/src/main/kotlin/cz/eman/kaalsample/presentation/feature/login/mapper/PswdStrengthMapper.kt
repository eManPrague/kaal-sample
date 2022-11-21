package cz.eman.kaalsample.presentation.feature.login.mapper

import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.presentation.feature.login.model.PswdStrengthVo

fun PswdStrength.toVo() : PswdStrengthVo {
    return when(this){
        PswdStrength.STRONG -> PswdStrengthVo(true,R.color.green, R.string.password_strong)
        PswdStrength.MEDIUM -> PswdStrengthVo(true,R.color.yellow, R.string.password_medium)
        PswdStrength.WEAK -> PswdStrengthVo(true,R.color.orange, R.string.password_weak)
        PswdStrength.SHORT -> PswdStrengthVo(false,R.color.red, R.string.password_short)
        PswdStrength.INVALID -> PswdStrengthVo(false,R.color.red, R.string.password_invalid)
        else -> PswdStrengthVo(false, R.color.red, R.string.password_error)
    }

}