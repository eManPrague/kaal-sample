package cz.eman.kaalsample.presentation.feature.login.mapper

import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrngth
import cz.eman.kaalsample.presentation.feature.login.model.PswdStrengthVo

fun PswdStrngth.toVo(): PswdStrengthVo = when (this) {
    PswdStrngth.NOT_OK -> PswdStrengthVo(id = R.string.app_name) // "nesplnuje pozadavky"
    PswdStrngth.OK -> PswdStrengthVo(id = null)
}