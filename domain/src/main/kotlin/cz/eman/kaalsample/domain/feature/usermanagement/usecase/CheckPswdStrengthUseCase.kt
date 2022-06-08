package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.model.OpenPsswd
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrngth

class CheckPswdStrengthUseCase(
    private val getUnsupportedChars: GetPswdUnsupportedCharsUseCase
) : UseCaseResult<PswdStrngth, OpenPsswd>() {

    override suspend fun doWork(params: OpenPsswd): Result<PswdStrngth> {
        return when {
            params.contains(getUnsupportedChars()) -> Result.Error(ErrorResult(message = "unsupported chars"))
            params.length < 5 -> Result.Success(PswdStrngth.LOW)
            else -> Result.Success(PswdStrngth.OK)
        }
    }
}