package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.usecases.UseCase
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository

class CheckPasswordStrengthUseCase(
    private val securityRepository: SecurityRepository
) : UseCase<PasswordStrength, String>() {

    override suspend fun doWork(params: String): PasswordStrength {

        val forbiddenChars = securityRepository.getForbiddenCharacters()
        val suggestedChars = securityRepository.getSuggestedCharacters()

        return when {
            forbiddenChars.any { it in params } -> PasswordStrength.Invalid

            params.length >= 7 && (
                        params.toCharArray().any(Character::isUpperCase) &&
                        params.toCharArray().any(Character::isLowerCase) &&
                        params.toCharArray().any(Character::isDigit) &&
                        suggestedChars.any { it in params }
                    ) -> PasswordStrength.Strong

            params.length >= 7 && (
                        params.toCharArray().any(Character::isUpperCase) ||
                        params.toCharArray().any(Character::isLowerCase) ||
                        params.toCharArray().any(Character::isDigit) ||
                        suggestedChars.any { it in params }
                    ) -> PasswordStrength.Medium

            params.isEmpty() -> PasswordStrength.Empty

            else -> PasswordStrength.Weak
        }
    }
}