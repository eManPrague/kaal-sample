package cz.eman.kaalsample.domain.feature.usermanagement.model

sealed class PasswordStrength {

    object Invalid : PasswordStrength()

    object Empty : PasswordStrength()

    object Weak : PasswordStrength()

    object Medium : PasswordStrength()

    object Strong : PasswordStrength()
}
