package cz.eman.kaalsample.data.feature.usermanagement.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthLocalDataSource
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthRemoteDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository

class PswdStrengthRemoteRepositoryImpl(
) : PswdStrengthRemoteDataSource {

    private var _invalidChars = "\\|\"~`"
    private var _minPswdLength = 6

    override suspend fun getInvalidCharsInPswd(): Result<String> {
        // TODO: Return data from DB
        return Result.Success(_invalidChars)
    }

    override suspend fun getMinPswdLength(): Result<Int> {
        // TODO: Return data from DB
        return Result.Success(_minPswdLength)
    }
}