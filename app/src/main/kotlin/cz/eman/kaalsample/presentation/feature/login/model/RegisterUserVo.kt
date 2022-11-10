package cz.eman.kaalsample.presentation.feature.login.model

import androidx.annotation.StringRes


data class RegisterUserVo(
    val userName: String,
    val pswd: String,
    @StringRes
    val error: Int?,
)

