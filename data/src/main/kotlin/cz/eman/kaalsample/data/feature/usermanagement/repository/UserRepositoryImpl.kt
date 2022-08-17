package cz.eman.kaalsample.data.feature.usermanagement.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.data.feature.datasource.SecurityDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.model.StrongPswdChars
import cz.eman.kaalsample.domain.feature.usermanagement.model.User
import cz.eman.kaalsample.domain.feature.usermanagement.repository.UserRepository
import cz.eman.kaalsample.domain.feature.usermanagement.source.UserDataSource

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val remoteSecurityDataSource: SecurityDataSource,
    private val localSecurityDataSource: SecurityDataSource,

    ) : UserRepository {

    override suspend fun authorizeUser(user: User) = userDataSource.authorizeUser(user)

    override suspend fun registerUser(user: User) = userDataSource.registerUser(user)

    override suspend fun getForbidencharsInPswd() = " &"

    override suspend fun getMinPswdLength(): Int {
        val result = remoteSecurityDataSource.getMinPswdLength()
        return if (result is Result.Success) {
            result.data
        } else {
            DEFAULT_PSWD_MIN_LENGTH
        }
    }

    override suspend fun getSecialCharsInPswd(): Result<StrongPswdChars> {
        val localData = localSecurityDataSource.getSecialCharsInPswd()
        if (localData is Result.Success) {
            return localData
        }
        val remoteResult = remoteSecurityDataSource.getSecialCharsInPswd()
        if (remoteResult is Result.Success) {
            localSecurityDataSource.setSpecialCharsInPswd(remoteResult.data)
        }
        return remoteResult
    }

    companion object {
        const val DEFAULT_PSWD_MIN_LENGTH = 7
    }

}