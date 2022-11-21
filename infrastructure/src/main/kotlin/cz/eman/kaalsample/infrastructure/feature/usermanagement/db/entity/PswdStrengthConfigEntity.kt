package cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PSWD_STRENGTH_CONFIG")
data class PswdStrengthConfigEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val invalidChars: String,
    val pswdLength: Int
)
