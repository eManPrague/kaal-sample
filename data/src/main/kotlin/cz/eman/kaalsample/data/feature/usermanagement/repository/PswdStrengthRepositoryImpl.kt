package cz.eman.kaalsample.data.feature.usermanagement.repository

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onError
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

    private suspend fun fetchInvalidCharsFromRemote(): Result<String> {
        val result = remoteDataSource.getInvalidCharsInPswd()
        result.onSuccess {
            localDataSource.saveInvalidCharsInPswd(it)
            return Result.Success(it)
        }.onError {
            return Result.Error(it)
        }
        // TODO: Use onFinished or is there any clever (should be) solution?
        return Result.Error(ErrorResult(message = "Error"))
    }

    override suspend fun getMinPswdLength(): Result<Int> {
        localDataSource.getMinPswdLength().onSuccess {
            return Result.Success(it)
        }
        return fetchMinPswdLengthFromRemote()
    }

    private suspend fun fetchMinPswdLengthFromRemote(): Result<Int> {
        val result = remoteDataSource.getMinPswdLength()
        result.onSuccess {
            localDataSource.saveMinPswdLength(it)
            return Result.Success(it)
        }.onError {
            return Result.Error(it)
        }
        // TODO: Use onFinished or is there any clever (should be) solution?
        return Result.Error(ErrorResult(message = "Error"))
    }
}