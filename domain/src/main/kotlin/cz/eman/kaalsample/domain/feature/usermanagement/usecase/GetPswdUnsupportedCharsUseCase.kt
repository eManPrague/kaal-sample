package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.usecases.UseCaseNoParams
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository


class GetPswdUnsupportedCharsUseCase(
    val repo: SecurityRepository
) : UseCaseNoParams<String>() {

    override suspend fun doWork(params: Unit): String {
        return repo.getPswdUnssuportedChars()
    }

}