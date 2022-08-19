package cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwordForbiddenChars")
data class PasswordForbiddenCharsEntity(
    @PrimaryKey
    val id: Int,
    val chars: String
) {

}