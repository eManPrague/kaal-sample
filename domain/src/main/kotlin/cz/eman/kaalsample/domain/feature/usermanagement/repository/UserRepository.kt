package cz.eman.kaalsample.domain.feature.usermanagement.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.StrongPswdChars
import cz.eman.kaalsample.domain.feature.usermanagement.model.User

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
interface UserRepository {

    suspend fun authorizeUser(user: User): Result<User>

    suspend fun registerUser(user: User): Result<User>

    suspend fun getMinPswdLength(): Int

    suspend fun getForbidencharsInPswd(): String

    suspend fun getSecialCharsInPswd() : Result<StrongPswdChars>
}