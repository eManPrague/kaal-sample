package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrngth
import cz.eman.kaalsample.domain.feature.usermanagement.repository.UserRepository

class CheckPswdStrengthUseCase(
    private val repository: UserRepository
) : UseCaseResult<PswdStrngth, String>() {

    override suspend fun doWork(params: String): Result<PswdStrngth> {
        return when {

            params.contains(repository.getForbidencharsInPswd()) -> Result.Error(
                ErrorResult(
                    code = PswdErrors.FORBIDDEN_CHARS,
                    message = "password contians forbidden characters"
                )
            )

            else -> Result.Error(ErrorResult(code = PswdErrors.PSWD_CAN_NOT_PROCESS))
        }
    }


}

enum class PswdErrors(override val value: Int) : ErrorCode {
    FORBIDDEN_CHARS(1),
    PSWD_CAN_NOT_PROCESS(2)
}