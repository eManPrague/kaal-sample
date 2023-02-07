package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.usecases.UseCase
import cz.eman.kaalsample.domain.feature.usermanagement.model.Password
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength

class CheckPswdStrengthUseCase(
    private val checkPswdStrengthUseCase: CheckPswdStrengthUseCase
) : UseCase<PswdStrength, Password>() {

    override suspend fun doWork(params: Password): PswdStrength {
        return when {
            params.pswd.length == 0 -> PswdStrength.EMPTY
            else -> PswdStrength.STRONG
        }
    }
}