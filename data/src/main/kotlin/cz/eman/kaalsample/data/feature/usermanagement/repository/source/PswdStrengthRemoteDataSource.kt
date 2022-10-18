package cz.eman.kaalsample.data.feature.usermanagement.repository.source

import cz.eman.kaal.domain.result.Result

interface PswdStrengthRemoteDataSource {

    suspend fun getInvalidCharsInPswd(): Result<String>
}