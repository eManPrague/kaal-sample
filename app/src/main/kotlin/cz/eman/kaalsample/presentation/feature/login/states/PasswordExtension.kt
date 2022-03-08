package cz.eman.kaalsample.presentation.feature.login.states

import cz.eman.kaalsample.R
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordStrength

fun PasswordStrength.toPasswordState(): PasswordStrengthState =
    when (this) {
        is PasswordStrength.Invalid ->
            PasswordStrengthState(
                R.string.password_strength_invalid,
                R.color.password_strength_invalid,
                R.drawable.ic_baseline_cancel_24
            )
        is PasswordStrength.Empty ->
            PasswordStrengthState(
                R.string.password_strength_empty,
                R.color.password_strength_invalid,
                R.drawable.ic_baseline_cancel_24
            )
        is PasswordStrength.Weak ->
            PasswordStrengthState(
                R.string.password_strength_weak,
                R.color.password_strength_weak,
                R.drawable.ic_baseline_cancel_24
            )
        is PasswordStrength.Medium ->
            PasswordStrengthState(
                R.string.password_strength_medium,
                R.color.password_strength_medium,
                R.drawable.ic_baseline_error_24
            )
        is PasswordStrength.Strong ->
            PasswordStrengthState(
                R.string.password_strength_strong,
                R.color.password_strength_strong,
                R.drawable.ic_baseline_check_circle_24
            )
        else -> PasswordStrengthState(
            R.string.password_strength_unknown,
            R.color.password_strength_unknown,
            R.drawable.ic_baseline_cancel_24
        )
    }
