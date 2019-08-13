package features.usermanagement.source

import cz.eman.kaal.domain.Result
import features.usermanagement.model.User

/**
 * @author PavelHabzansky (pavel.habzansky@eman.cz) 2019-08-06.
 */
interface UserDataSource {

    suspend fun authorizeUser(user: User): Result<User>

    suspend fun registerUser(user: User): Result<User>

}