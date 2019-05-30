package cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
@Entity(tableName = "user")
data class UserEntity(

    @PrimaryKey
    val username: String,
    val password: String
)