package cz.eman.kaalsample.data.feature.datasource

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.StrongPswdChars

interface SecurityDataSource {

    suspend fun getMinPswdLength(): Result<Int>

    suspend fun getSecialCharsInPswd(): Result<StrongPswdChars>

    suspend fun setSpecialCharsInPswd(chars: StrongPswdChars)
}