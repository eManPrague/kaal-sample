package cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.eman.kaalsample.data.common.room.BaseDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.PasswordForbiddenCharsEntity
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.UserEntity

@Dao
interface SecurityRequirementsDao : BaseDao<PasswordForbiddenCharsEntity> {

    @Query("SELECT * FROM passwordForbiddenChars LIMIT 1")
    suspend fun getForbiddenCharacters(): PasswordForbiddenCharsEntity

    @Insert
    suspend fun setForbiddenCharacters(characters : PasswordForbiddenCharsEntity)


}