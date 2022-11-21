package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.onError
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.model.EncriptedPswd
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository

class CheckPswdStrengthUseCase(
    private val repository: PswdStrengthRepository
) : UseCaseResult<PswdStrength, EncriptedPswd>() {

    override suspend fun doWork(params: EncriptedPswd): Result<PswdStrength> {
        val pswd = params.pswd
        val pswdLength = pswd.length

        when {
            pswdLength == 0 -> return Result.success(PswdStrength.SHORT)
            else -> repository.getPswdStrengthConfig().onSuccess { config ->
                if (pswdLength < config.pswdLength) return Result.success(PswdStrength.SHORT)
                if (config.invalidChars.any{char -> pswd.contains(char)}) return Result.success(PswdStrength.INVALID)
            }.onError {
                return Result.error(it)
            }
        }

        return Result.success(getPwdStrengthScale(pswd))

    }

    /**
     * simplified from https://www.uic.edu/apps/strong-password/
     */
    private fun getPwdStrengthScale(pswd: String) : PswdStrength {
        val pswdLength = pswd.length
        val score = (pswdLength * 4) +
                ((pswdLength - pswd.filter{ char -> char.isUpperCase() }.length) * 2) +
                ((pswdLength - pswd.filter{ char -> char.isLowerCase() }.length) * 2) +
                (pswd.filter{ char -> char.isDigit() }.length * 4) +
                (pswd.filter { char -> !char.isLetterOrDigit() }.length * 6 )
        return when (score) {
            in 1..50 -> PswdStrength.WEAK
            in 51..100 -> PswdStrength.MEDIUM
            else -> PswdStrength.STRONG
        }

    }

}

enum class PswdErrors(override val value: Int) : ErrorCode {
    ERROR_PROCESSING_PSWD(1)
}