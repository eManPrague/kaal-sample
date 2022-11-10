package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onError
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.model.EncriptedPswd
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository


class CheckPswdStrengthUseCase(
    private val repository: PswdStrengthRepository
) : UseCaseResult<PswdStrength, EncriptedPswd>() {

    override suspend fun doWork(encriptedPswd: EncriptedPswd): Result<PswdStrength> {
        val pswdLength = encriptedPswd.pswd.length
        if (pswdLength == 0)
            return Result.Success(PswdStrength.INVALID)

        repository.getMinPswdLength().onSuccess {
            if(pswdLength < it)
                return Result.Success(PswdStrength.SHORT)
        }.onError {
            return Result.Error(it)
        }

        repository.getInvalidCharsInPswd().onSuccess {invalidChars ->
            if(invalidChars.any {encriptedPswd.pswd.contains(it)})
                return Result.Success(PswdStrength.INVALID)
        }.onError {
            return Result.Error(it)
        }

        return calculateStrength(encriptedPswd.pswd)
    }

    //INFO: https://stackoverflow.com/questions/32883969/android-password-strength-checker-with-seekbar
    private fun calculateStrength(password: String): Result<PswdStrength> {
        var currentScore = 0
        var sawUpper = false
        var sawLower = false
        var sawDigit = false
        var sawSpecial = false
        for (element in password) {
            if (!sawSpecial && !Character.isLetterOrDigit(element)) {
                currentScore += 1
                sawSpecial = true
            } else {
                if (!sawDigit && Character.isDigit(element)) {
                    currentScore += 1
                    sawDigit = true
                } else {
                    if (!sawUpper || !sawLower) {
                        if (Character.isUpperCase(element)) sawUpper = true else sawLower = true
                        if (sawUpper && sawLower) currentScore += 1
                    }
                }
            }
        }

        return when (currentScore) {
            0 -> return Result.Success(PswdStrength.WEAK)
            1 -> return Result.Success(PswdStrength.MEDIUM)
            2,3 -> return Result.Success(PswdStrength.STRONG)
            else -> Result.Error(ErrorResult(message = "Something went wrong"))
        }
    }
}