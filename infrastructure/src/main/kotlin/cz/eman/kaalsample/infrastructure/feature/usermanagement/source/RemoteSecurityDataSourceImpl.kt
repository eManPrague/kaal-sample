package cz.eman.kaalsample.infrastructure.feature.usermanagement.source

import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.data.feature.datasource.SecurityDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText

class RemoteSecurityDataSourceImpl : SecurityDataSource {

    override suspend fun getMinPasswordLength(): Result<Int> {
        return Result.success(7)
    }

    override suspend fun getForbiddenPasswordCharacters(): Result<PasswordText> {
        return Result.success(PasswordText(" @;&$"))
    }

    override suspend fun setForbiddenPasswordCharacters(chars: PasswordText) {
        Result.error(ErrorResult(object : ErrorCode {
            override val value: Int
                get() = -1
        }), null)
    }
}