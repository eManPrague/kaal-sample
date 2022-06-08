package cz.eman.kaalsample.presentation.feature.login.model

import androidx.annotation.StringRes
import cz.eman.kaalsample.domain.feature.usermanagement.model.User

data class LoginModelVo(
    val mode: Boolean,
    val user: User,
    @StringRes
    val pswdError: Int
)