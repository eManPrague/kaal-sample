package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.model.PassStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class CheckPassStrengthUseCase(private val securityRepository: SecurityRepository) :
    UseCaseResult<PassStrength, CheckPassStrengthUseCase.PasswordParams>() {

    override suspend fun doWork(params: PasswordParams): Result<PassStrength> {

        val invalidCharacters = securityRepository.getInvalidCharacters()
        val minPasswordLength = securityRepository.getMinCharactersCount()

        when {
            invalidCharacters.any { it in params.password } -> PassStrength.INVALID

            params.password.length < minPasswordLength -> PassStrength.WEAK

            params.password.length > minPasswordLength -> PassStrength.STRONG

            else -> PassStrength.NORMAL
        }.let {
            return cz.eman.kaal.domain.result.Result.Companion.success(it)
        }

    }

    data class PasswordParams(val password: String)
}