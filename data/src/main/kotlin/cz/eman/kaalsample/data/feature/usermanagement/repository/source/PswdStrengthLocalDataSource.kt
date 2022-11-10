package cz.eman.kaalsample.data.feature.usermanagement.repository.source

import cz.eman.kaal.domain.result.Result

interface  PswdStrengthLocalDataSource {

    suspend fun getInvalidCharsInPswd(): Result<String>

    suspend fun saveInvalidCharsInPswd(invalidChars: String)

    suspend fun getMinPswdLength(): Result<Int>

    suspend fun saveMinPswdLength(pswdMinLength: Int)
}