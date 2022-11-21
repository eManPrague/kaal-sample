package cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.eman.kaalsample.data.common.room.BaseDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.PswdStrengthConfigEntity

@Dao
interface PswdStrengthConfigDao : BaseDao<PswdStrengthConfigEntity> {
    @Query("SELECT * FROM PSWD_STRENGTH_CONFIG")
    suspend fun getPswdStrengthConfig(): PswdStrengthConfigEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setPswdStrengthConfig(config: PswdStrengthConfigEntity)
}