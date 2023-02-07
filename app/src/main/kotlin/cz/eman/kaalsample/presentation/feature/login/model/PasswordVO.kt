package cz.eman.kaalsample.presentation.feature.login.model

import androidx.annotation.StringRes
import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength

data class PasswordVO(@StringRes val pswdStrength: Int? = null)


fun PswdStrength.toVO(): PasswordVO {
    val stringres = when (this) {
        PswdStrength.INVALID -> R.string.pswd_invalid
        PswdStrength.EMPTY -> R.string.pswd_empty
        else -> null
    }
    return PasswordVO(stringres)
}
