package cz.eman.kaalsample.presentation.feature.login.extension

import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength

fun PswdStrength.toVo() = when (this) {
    PswdStrength.BAD -> R.string.login_screen_login
    PswdStrength.GOD -> R.string.androidx_startup
    PswdStrength.MID -> R.string.app_name
}