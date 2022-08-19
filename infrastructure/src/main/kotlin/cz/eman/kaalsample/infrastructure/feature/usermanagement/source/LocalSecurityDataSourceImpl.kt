package cz.eman.kaalsample.infrastructure.feature.usermanagement.source

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.data.feature.datasource.SecurityDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao.SecurityRequirementsDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.toDTO
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.toEntity

class LocalSecurityDataSourceImpl(
    private val securityDao: SecurityRequirementsDao
) : SecurityDataSource {

    override suspend fun getMinPasswordLength(): Result<Int> {
        return Result.success(7)
    }

    override suspend fun getForbiddenPasswordCharacters(): Result<PasswordText> {
        val result = securityDao.getForbiddenCharacters()
        return if (result.chars.isEmpty()){
            Result.error(ErrorResult(message = "Could not find data locally"))
        } else
            Result.Success(result.toDTO())
    }

    override suspend fun setForbiddenPasswordCharacters(chars: PasswordText) {
        securityDao.setForbiddenCharacters(chars.toEntity())
    }

}