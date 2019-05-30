package cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.eman.kaalsample.data.common.room.BaseDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.UserEntity

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM user WHERE username = :username and password = :password")
    suspend fun findUser(username: String, password: String): UserEntity?

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUserByName(username: String): UserEntity?

}