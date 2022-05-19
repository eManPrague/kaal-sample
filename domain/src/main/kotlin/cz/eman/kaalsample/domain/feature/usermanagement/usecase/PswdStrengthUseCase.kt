package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository

class PswdStrengthUseCase(
    private val repo: SecurityRepository
) : UseCaseResult<PswdStrength, String>() {

    override suspend fun doWork(params: String): Result<PswdStrength> {

        return when {
            params.length > 7 && params.contains(repo.getSepcialChars()) -> Result.Success(data = PswdStrength.GOD)
            params.length > 7 -> Result.Success(data = PswdStrength.MID)
            else -> Result.Error(ErrorResult(code = ErrorCode.UNDEFINED))
        }
    }

}