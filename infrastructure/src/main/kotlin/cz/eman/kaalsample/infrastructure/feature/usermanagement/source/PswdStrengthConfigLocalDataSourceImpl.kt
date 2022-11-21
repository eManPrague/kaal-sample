package cz.eman.kaalsample.infrastructure.feature.usermanagement.source

import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.data.feature.usermanagement.repository.source.PswdStrengthConfigLocalDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrengthConfig
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.PswdErrors
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao.PswdStrengthConfigDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.toDo
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.toEntity

class PswdStrengthConfigLocalDataSourceImpl(
    private val pswdStrengthConfigDao: PswdStrengthConfigDao
): PswdStrengthConfigLocalDataSource {

    override suspend fun getPswdStrengthConfig(): Result<PswdStrengthConfig> {
    //return Result.success(PswdStrengthConfig(""" \"#$'/[]^{|}""",8)) //test without DAO
    val config = pswdStrengthConfigDao.getPswdStrengthConfig()
        return if (config != null) {
            Result.success(config.toDo())
        } else {
            Result.error(ErrorResult(code = PswdErrors.ERROR_PROCESSING_PSWD))
        }
    }

    override suspend fun savePswdStrengthConfig(config: PswdStrengthConfig) {
        pswdStrengthConfigDao.setPswdStrengthConfig(config.toEntity())
    }

}