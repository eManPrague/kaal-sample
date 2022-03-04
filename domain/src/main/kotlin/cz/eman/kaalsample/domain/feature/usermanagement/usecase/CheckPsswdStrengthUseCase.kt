package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.usecases.UseCase
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository

class CheckPsswdStrengthUseCase(
    private val repository: SecurityRepository
) : UseCase<PswdStrength, String>() {

    override suspend fun doWork(params: String): PswdStrength {
        // todo
        val forbiden = repository.getFch()
        val suggested = repository.getSch()

        return PswdStrength.Invalid
    }

}