package cz.eman.kaalsample.domain.feature.usermanagement.usecase

import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaalsample.domain.feature.usermanagement.model.User
import cz.eman.kaalsample.domain.feature.usermanagement.repository.UserRepository

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class RegisterUserUseCase(private val userRepository: UserRepository) :
        UseCaseResult<User, RegisterUserUseCase.Params>() {

    override suspend fun doWork(params: Params) = userRepository.registerUser(user = params.user)

    data class Params(val user: User)
}