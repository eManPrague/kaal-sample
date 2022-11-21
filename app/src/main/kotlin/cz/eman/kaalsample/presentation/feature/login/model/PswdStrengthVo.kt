package cz.eman.kaalsample.presentation.feature.login.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class PswdStrengthVo (
    val allowedPassword: Boolean,
    @ColorRes
    val color: Int,
    @StringRes
    val message: Int
)