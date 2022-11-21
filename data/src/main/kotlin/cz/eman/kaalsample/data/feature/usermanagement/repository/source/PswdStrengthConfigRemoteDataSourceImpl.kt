package cz.eman.kaalsample.data.feature.usermanagement.repository.source

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrengthConfig

class PswdStrengthConfigRemoteDataSourceImpl: PswdStrengthConfigRemoteDataSource {

    override suspend fun getPswdStrengthConfig(): Result<PswdStrengthConfig> {
        return Result.Error(ErrorResult(message="Remote Data source not in scope"))
    }
}