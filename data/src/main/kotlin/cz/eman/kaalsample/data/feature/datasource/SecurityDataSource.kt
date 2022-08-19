package cz.eman.kaalsample.data.feature.datasource

import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText

interface  SecurityDataSource {

    suspend fun getMinPasswordLength(): cz.eman.kaal.domain.result.Result<Int>

    suspend fun getForbiddenPasswordCharacters(): cz.eman.kaal.domain.result.Result<PasswordText>

    suspend fun setForbiddenPasswordCharacters(chars: PasswordText)

}