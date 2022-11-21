package cz.eman.kaalsample.data.feature.usermanagement.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthConfigLocalDataSource
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthConfigRemoteDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrengthConfig
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository

class PswdStrengthRepositoryImpl(
    private val localDataSource: PswdStrengthConfigLocalDataSource,
    private val remoteDataSource: PswdStrengthConfigRemoteDataSource
) : PswdStrengthRepository {

    override suspend fun getPswdStrengthConfig(): Result<PswdStrengthConfig> {
        localDataSource.getPswdStrengthConfig().onSuccess {
            return Result.Success(it)
        }
        return fetchPswdStrengthConfigFromRemote()
    }

    private suspend fun fetchPswdStrengthConfigFromRemote(): Result<PswdStrengthConfig> {
        val result = remoteDataSource.getPswdStrengthConfig()
        result.onSuccess {
            localDataSource.savePswdStrengthConfig(it)
        }
        return result
    }

}