package cz.eman.kaalsample.infrastructure.feature.usermanagement.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.data.feature.datasource.SecurityDataSource


class RemoteSecurityDataSourceImpl : SecurityDataSource {
    override suspend fun getMinPswdLength(): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getSecialCharsInPswd(): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun setSpecialCharsInPswd(chars: String) {
        TODO("Not yet implemented")
    }
}