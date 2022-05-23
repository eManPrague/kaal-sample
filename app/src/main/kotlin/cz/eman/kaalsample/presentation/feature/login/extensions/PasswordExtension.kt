package cz.eman.kaalsample.presentation.feature.login.extensions

import cz.eman.kaalsample.domain.feature.usermanagement.model.PassStrength

fun PassStrength.toVo(): String {

    return when (this.name) {
        PassStrength.INVALID.toString() -> "Invalid password"
        PassStrength.WEAK.toString() -> "Weak password"
        PassStrength.NORMAL.toString() -> "Normal password"
        PassStrength.STRONG.toString() -> "Strong password"
        else -> ""
    }
}
