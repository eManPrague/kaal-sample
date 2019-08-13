package features.usermanagement.usecases

import cz.eman.kaal.domain.usecases.UseCaseResult
import features.usermanagement.model.User
import features.usermanagement.repository.UserRepository

/**
 * @author PavelHabzansky (pavel.habzansky@eman.cz) 2019-08-06.
 */
class AuthorizeUserUseCase(private val userRepository: UserRepository) :
        UseCaseResult<User, AuthorizeUserUseCase.Params>() {

    override suspend fun doWork(params: Params) = userRepository.authorizeUser(user = params.user)

    data class Params(val user: User)
}