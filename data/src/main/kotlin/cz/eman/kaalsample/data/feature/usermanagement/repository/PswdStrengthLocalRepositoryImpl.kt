package cz.eman.kaalsample.data.feature.usermanagement.repository

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthLocalDataSource

class PswdStrengthLocalRepositoryImpl(
) : PswdStrengthLocalDataSource {

    private var _invalidChars = ""
    private var _minPswdLength = -1

    override suspend fun getInvalidCharsInPswd(): Result<String> {
        if(_invalidChars.isNotEmpty())
            return Result.Success(_invalidChars)
        return Result.Error(ErrorResult(message = "No data"))
    }

    override suspend fun saveInvalidCharsInPswd(invalidChars: String) {
        _invalidChars = invalidChars
    }

    override suspend fun getMinPswdLength(): Result<Int> {
        if (_minPswdLength >= 0)
            return Result.Success(_minPswdLength)
        return Result.Error(ErrorResult(message = "No data"))
    }

    override suspend fun saveMinPswdLength(minPswdLength: Int) {
        _minPswdLength = minPswdLength
        // TODO: Throw error if minPswdLength < 0
    }
}