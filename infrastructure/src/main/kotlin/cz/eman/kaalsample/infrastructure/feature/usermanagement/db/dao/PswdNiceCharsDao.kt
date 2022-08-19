package cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.eman.kaalsample.data.common.room.BaseDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.PswdNiceCharsEntity

@Dao
interface PswdNiceCharsDao : BaseDao<PswdNiceCharsEntity> {
    @Query("SELECT * FROM pswdchars")
    suspend fun getSpecChars(): PswdNiceCharsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecChars(specChars: PswdNiceCharsEntity)
}