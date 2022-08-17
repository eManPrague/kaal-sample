package cz.eman.kaalsample.infrastructure.feature.usermanagement.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.data.feature.datasource.SecurityDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.model.StrongPswdChars
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao.PswdNiceCharsDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.toEntity

class LocalSecurityDataSourceImpl(
    private val pswdDao: PswdNiceCharsDao
) : SecurityDataSource {

    override suspend fun getMinPswdLength(): Result<Int> {
        pswdDao.get
    }

    override suspend fun getSecialCharsInPswd(): Result<StrongPswdChars> {
        val getData = pswdDao.get()

        return if(getData.nic){
            result error
        }else {
            Result.Success(.toDo)
        }
    }

    override suspend fun setSpecialCharsInPswd(chars: StrongPswdChars) {
        pswdDao.set(chars.toEntity())
    }
}