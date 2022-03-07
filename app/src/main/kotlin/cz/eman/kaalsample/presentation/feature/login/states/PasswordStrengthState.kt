package cz.eman.kaalsample.presentation.feature.login.states

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PasswordStrengthState(
    @StringRes val errorTextId: Int,
    @ColorRes val textColorId: Int,
    @DrawableRes val iconSourceId: Int
    )
