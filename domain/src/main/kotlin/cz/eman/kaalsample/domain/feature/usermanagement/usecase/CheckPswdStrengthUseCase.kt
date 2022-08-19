package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.UserRepository

class CheckPswdStrengthUseCase(
    private val repository: UserRepository
) : UseCaseResult<PswdStrength, String>() {

    override suspend fun doWork(params: String): Result<PswdStrength> {

        val specialCharsInPswd = repository.getSpecialCharsInPswd().toRegex()

        return when {

            params.contains(repository.getForbiddencharsInPswd()) -> Result.Success(PswdStrength.INVALID)

            params.contains(specialCharsInPswd) && params.length >= repository.getMinPswLength() -> Result.Success(PswdStrength.OK)

            !params.contains(specialCharsInPswd) && params.length >= repository.getMinPswLength() -> Result.Success(PswdStrength.MEDIUM)

            !params.contains(specialCharsInPswd) && params.length < repository.getMinPswLength() && params.isNotEmpty() -> Result.Success(PswdStrength.LOW)

            else -> Result.Error(ErrorResult(code = PswdErrors.PSWD_CAN_NOT_PROCESS, message = "The password cannot be empty"))
        }
    }
}

enum class PswdErrors(override val value: Int) : ErrorCode {
    PSWD_CAN_NOT_PROCESS(1)
}