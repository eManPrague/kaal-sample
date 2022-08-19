package cz.eman.kaalsample.presentation.feature.login.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import cz.eman.kaalsample.R

data class PswdStrengthVo (
    @StringRes val id: Int,
    @ColorRes val colorRes: Int = R.color.pswd_error
)