package cz.eman.kaalsample.domain.feature.usermanagement.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrengthConfig

interface PswdStrengthRepository {

    suspend fun getPswdStrengthConfig(): Result<PswdStrengthConfig>

}