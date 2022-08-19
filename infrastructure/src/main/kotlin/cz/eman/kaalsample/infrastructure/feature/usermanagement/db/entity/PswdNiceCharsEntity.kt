package cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pswdchars")
data class PswdNiceCharsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chars: String,
    val length: Int = 7
)