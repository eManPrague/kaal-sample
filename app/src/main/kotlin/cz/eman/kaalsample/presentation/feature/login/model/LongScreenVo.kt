package cz.eman.kaalsample.presentation.feature.login.model

import androidx.annotation.StringRes

data class LongScreenVo(
    val userName: String,
    val password: String,
    val loginButtonPreffered: Boolean,
    val registerButtonPreferred: Boolean,
    @StringRes
    val passwordStrengthText: Int?,
)
