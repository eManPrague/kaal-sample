package cz.eman.kaalsample.presentation.feature.login.mapper

import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPasswordStrengthUseCase.PasswordState
import cz.eman.kaalsample.presentation.feature.login.model.PasswordStrengthVo

fun PasswordState.toPasswordStrengthVo() : PasswordStrengthVo= when(this){
    PasswordState.EMPTY, PasswordState.SHORT -> PasswordStrengthVo(
        textId = R.string.login_password_short,
        styleId = R.style.errorAppearance,
        isAcceptable = false
    )
    PasswordState.FORBIDDEN_CHARS -> PasswordStrengthVo(
        textId = R.string.login_password_forbidden_characters,
        styleId = R.style.errorAppearance,
        isAcceptable = false
    )
    PasswordState.WEAK -> PasswordStrengthVo(
        textId =  R.string.login_password_weak,
        styleId = R.style.warningAppearance,
        isAcceptable = true
    )
    PasswordState.NORMAL -> PasswordStrengthVo(
        textId =  R.string.login_password_normal,
        styleId = R.style.normalAppearance,
        isAcceptable = true
    )
    PasswordState.STRONG -> PasswordStrengthVo(
        textId = R.string.login_password_strong,
        styleId = R.style.amazingAppearance,
        isAcceptable = true
    )
    else -> { PasswordStrengthVo(null, null)
    }
}