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
    private val localSecurityDataSource: SecurityDataSource
) : UserRepository {

    //Minimum 7 characters, at least one uppercase letter, one lowercase letter, one number and one special character
    private val defaultRegex = ("""^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?])[A-Za-z\d@!%*?]{7,}$""".toRegex()).toString()

    override suspend fun authorizeUser(user: User) = userDataSource.authorizeUser(user)

    override suspend fun registerUser(user: User) = userDataSource.registerUser(user)

    override suspend fun getForbiddencharsInPswd() = "&"

    override suspend fun getMinPswLength(): Int {
        val result = localSecurityDataSource.getMinPswdLength()
        return if (result is Result.Success) {
            result.data
        } else {
            return DEFAULT_PSWD_MIN_LENGTH
        }
    }

    override suspend fun getSpecialCharsInPswd(): String {
        val localData = localSecurityDataSource.getSpecialCharsInPswd()
        return if (localData is Result.Success) {
            localData.data.chars
        } else {
            localSecurityDataSource.setSpecialCharsInPswd(StrongPswdChars(defaultRegex))
            defaultRegex
        }
    }

    companion object {
        const val DEFAULT_PSWD_MIN_LENGTH = 7
    }
}