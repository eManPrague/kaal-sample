package cz.eman.kaalsample.presentation.feature.login.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes

data class PasswordStrengthVo(
    @StringRes val textId: Int?,
    @StyleRes val styleId: Int?,
    val isAcceptable: Boolean = false
    ) {

}