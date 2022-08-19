package cz.eman.kaalsample.infrastructure.feature.usermanagement.source

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.data.feature.datasource.SecurityDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.model.StrongPswdChars
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.PswdErrors
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao.PswdNiceCharsDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.toDo
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.toEntity

class LocalSecurityDataSourceImpl(
    private val pswdDao: PswdNiceCharsDao
) : SecurityDataSource {

    override suspend fun getMinPswdLength(): Result<Int> {
        val getLength = pswdDao.getSpecChars()?.length
        return if (getLength == null) {
            Result.Error(ErrorResult(code = PswdErrors.PSWD_CAN_NOT_PROCESS))
        } else {
            Result.Success(getLength)
        }
    }

    override suspend fun getSpecialCharsInPswd(): Result<StrongPswdChars> {
        val getData = pswdDao.getSpecChars()
        return if (getData == null) {
            Result.Error(ErrorResult(code = PswdErrors.PSWD_CAN_NOT_PROCESS))
        } else {
            Result.Success(getData.toDo())
        }
    }

    override suspend fun setSpecialCharsInPswd(chars: StrongPswdChars) {
        pswdDao.insertSpecChars(chars.toEntity())
    }
}