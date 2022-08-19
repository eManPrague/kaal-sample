package cz.eman.kaalsample.data.feature.usermanagement.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaalsample.data.feature.datasource.SecurityDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository

class SecurityRepositoryImpl(
    private val remoteSecurityDataSource: SecurityDataSource,
    private val localSecurityDataSource: SecurityDataSource
) : SecurityRepository {

    override suspend fun getForbiddenCharsInPswd() = " @,;&"

    override suspend fun getMinPasswordLength(): Result<Int> {
        val localResult = localSecurityDataSource.getMinPasswordLength()
        localResult.onSuccess {
            return Result.success(it)
        }

        val remoteResult = remoteSecurityDataSource.getMinPasswordLength()
        if (remoteResult is Result.Success){
            return Result.success(remoteResult.data)
        }

        return Result.success(DEFAULT_PSWD_MIN_LENGTH)

    }

    override suspend fun getForbiddenPasswordChars(): Result<PasswordText> {

        val localResult = localSecurityDataSource.getForbiddenPasswordCharacters()
        if (localResult is Result.Success){
            return localResult
        }

        val remoteResult = remoteSecurityDataSource.getForbiddenPasswordCharacters()
        if (remoteResult is Result.Success){
            localSecurityDataSource.setForbiddenPasswordCharacters(remoteResult.data)
        }

        return remoteResult
    }

    companion object {
        const val DEFAULT_PSWD_MIN_LENGTH = 7
    }

}