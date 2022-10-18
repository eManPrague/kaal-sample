package cz.eman.kaalsample.domain.feature.usermanagement.repository

import cz.eman.kaal.domain.result.Result

interface PswdStrengthRepository {

    suspend fun getInvalidCharsInPswd(): Result<String>

    suspend fun getMinPswdLength(): Int

}