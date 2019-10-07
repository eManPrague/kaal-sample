package cz.eman.kaalsample.domain.feature.usermanagement.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.User

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
interface UserDataSource {

    suspend fun authorizeUser(user: User): Result<User>

    suspend fun registerUser(user: User): Result<User>

}