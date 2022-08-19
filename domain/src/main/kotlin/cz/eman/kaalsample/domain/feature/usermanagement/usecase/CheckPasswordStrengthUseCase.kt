package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onError
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
class CheckPasswordStrengthUseCase(
private val securityRepository: SecurityRepository
) : UseCaseResult<CheckPasswordStrengthUseCase.PasswordState, String>() {

    override suspend fun doWork(params: String): Result<PasswordState> {
        val passwordResult = securityRepository.getMinPasswordLength()
        var minPasswordLength = DEFAULT_MIN_PASSWORD_LENGTH
        passwordResult.onSuccess {
            minPasswordLength = it
        }

        var forbiddenChars = ""
        val forbiddenCharsResult = securityRepository.getForbiddenPasswordChars().onSuccess {  }
        forbiddenCharsResult.onSuccess {
            forbiddenChars = it.chars
        }.onError {
            return Result.error(ErrorResult(message = "Could not load forbidden characters"))
        }

        val paramsChars = params.toCharArray()

        return when {

            params.isBlank() -> Result.success(PasswordState.EMPTY)

            forbiddenChars.any { it in params } -> Result.success(PasswordState.FORBIDDEN_CHARS)

            params.length < minPasswordLength -> Result.success(PasswordState.SHORT)

            paramsChars.any(Character::isUpperCase) &&
                    paramsChars.any(Character::isLowerCase) &&
                    paramsChars.any(Character::isDigit) &&
                    paramsChars.size >= NORMAL_PASSWORD_MIN_LENGTH
            -> Result.success(PasswordState.STRONG)

            (paramsChars.any(Character::isUpperCase) &&
                    paramsChars.any(Character::isLowerCase) &&
                    !paramsChars.any(Character::isDigit)) ||
                    paramsChars.size >= NORMAL_PASSWORD_MIN_LENGTH
                -> Result.success(PasswordState.NORMAL)

            else -> Result.success(PasswordState.WEAK)
        }
    }

    companion object {
        const val DEFAULT_MIN_PASSWORD_LENGTH : Int = 7
        const val NORMAL_PASSWORD_MIN_LENGTH: Int = 13
    }

    enum class PasswordState {
        SHORT,
        FORBIDDEN_CHARS,
        WEAK,
        NORMAL,
        STRONG,
        EMPTY,
    }

}
