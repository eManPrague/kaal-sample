package cz.eman.kaalsample.data.feature.usermanagement.repository.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrengthConfig

interface PswdStrengthConfigLocalDataSource {

    suspend fun getPswdStrengthConfig(): Result<PswdStrengthConfig>

    suspend fun savePswdStrengthConfig(config: PswdStrengthConfig)
}