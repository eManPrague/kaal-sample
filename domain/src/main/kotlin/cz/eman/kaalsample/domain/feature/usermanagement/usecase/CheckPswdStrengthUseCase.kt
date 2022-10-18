package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.model.EncriptedPswd
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository

class CheckPswdStrengthUseCase(
    private val repository: PswdStrengthRepository
) : UseCaseResult<PswdStrength, EncriptedPswd>() {

    override suspend fun doWork(params: EncriptedPswd): Result<PswdStrength> {
        return TODO
    }


}