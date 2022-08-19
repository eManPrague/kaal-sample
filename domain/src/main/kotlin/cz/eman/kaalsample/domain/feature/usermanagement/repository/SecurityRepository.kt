package cz.eman.kaalsample.domain.feature.usermanagement.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText

interface SecurityRepository {

    suspend fun getMinPasswordLength() : Result<Int>

    suspend fun getForbiddenCharsInPswd(): String

    suspend fun getForbiddenPasswordChars() : Result<PasswordText>

}