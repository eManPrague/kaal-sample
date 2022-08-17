package cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pswdchars")
data class PswdNiceCharsEntity(
    @PrimaryKey
    val id: Int,
    val chars: String
)