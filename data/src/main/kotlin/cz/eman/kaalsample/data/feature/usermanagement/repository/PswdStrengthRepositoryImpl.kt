package cz.eman.kaalsample.data.feature.usermanagement.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthLocalDataSource
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthRemoteDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository

class PswdStrengthRepositoryImpl(
    private val localDataSource: PswdStrengthLocalDataSource,
    private val remoteDataSource: PswdStrengthRemoteDataSource
) : PswdStrengthRepository {

    override suspend fun getInvalidCharsInPswd(): Result<String> {
        localDataSource.getInvalidCharsInPswd().onSuccess {
            return Result.Success(it)
        }
        return fetchInvalidCharsFromRemote()
    }

    override suspend fun getMinPswdLength(): Int {
        return MIN_PSWD_LENGTH
    }

    companion object {
        const val MIN_PSWD_LENGTH = 7
    }

    private suspend fun fetchInvalidCharsFromRemote(): Result<String> {
        val result = remoteDataSource.getInvalidCharsInPswd()
        if (result is Result.Success) {
            localDataSource.saveInvalidCharsInPswd(result.data)
        }
        return result
    }
}