package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.usecases.UseCase
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordStrength

class CheckPasswordStrengthUseCase() : UseCase<PasswordStrength, String>() {

    @Suppress("NewApi")
    override suspend fun doWork(params: String): PasswordStrength {
        val forbiddenChars = listOf("/", ",", " ")
        return when {
            forbiddenChars.any { it in params } -> PasswordStrength.Invalid
            params.length >= 7 && (params.chars().anyMatch(Character::isUpperCase) &&
                    params.chars().anyMatch(Character::isLowerCase) &&
                    params.chars().anyMatch(Character::isDigit)) -> PasswordStrength.Strong
            params.length >= 7 && (params.chars().anyMatch(Character::isUpperCase) ||
                    params.chars().anyMatch(Character::isLowerCase) ||
                    params.chars().anyMatch(Character::isDigit)) -> PasswordStrength.Medium
            params.isEmpty() -> PasswordStrength.Empty
            else -> PasswordStrength.Weak
        }
    }
}