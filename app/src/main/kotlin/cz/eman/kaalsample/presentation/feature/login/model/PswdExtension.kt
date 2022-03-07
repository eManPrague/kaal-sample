package cz.eman.kaalsample.presentation.feature.login.model

import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength

fun PswdStrength.toPswdState(): PswdStateVo =
    when (this) {
        is PswdStrength.Invalid -> PswdStateVo(R.string.error_message_button_back)
        is PswdStrength.Medium -> PswdStateVo(R.string.error_message_button_back)
        else -> PswdStateVo(0)
    }