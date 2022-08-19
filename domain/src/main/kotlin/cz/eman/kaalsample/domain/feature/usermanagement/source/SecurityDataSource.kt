package cz.eman.kaalsample.domain.feature.usermanagement.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText

interface SecurityDataSource {

    suspend fun getMinPasswordLength() : Int

    suspend fun getForbiddenCharsInPswd(): String

    suspend fun getForbiddenPasswordChars() : Result<PasswordText>

}