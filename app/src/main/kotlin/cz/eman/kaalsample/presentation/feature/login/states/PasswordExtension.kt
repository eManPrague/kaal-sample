package cz.eman.kaalsample.presentation.feature.login.states

import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordStrength

fun PasswordStrength.toPasswordState() : PasswordStrengthState =
    when(this) {
        is PasswordStrength.Invalid ->
            PasswordStrengthState(
                R.string.password_strength_invalid,
                R.color.password_strength_invalid
            )
        is PasswordStrength.Weak ->
            PasswordStrengthState(
                R.string.password_strength_weak,
                R.color.password_strength_weak
            )
        is PasswordStrength.Medium ->
            PasswordStrengthState(
                R.string.password_strength_medium,
                R.color.password_strength_medium
            )
        is PasswordStrength.Strong ->
            PasswordStrengthState(
                R.string.password_strength_strong,
                R.color.password_strength_strong
            )
        else -> PasswordStrengthState(
            R.string.password_strength_unknown,
            R.color.password_strength_unknown
        )
    }