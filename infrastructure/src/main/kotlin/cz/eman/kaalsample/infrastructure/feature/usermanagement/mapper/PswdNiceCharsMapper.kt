package cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper

import cz.eman.kaalsample.domain.feature.usermanagement.model.StrongPswdChars
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.PswdNiceCharsEntity

fun StrongPswdChars.toEntity() = PswdNiceCharsEntity(
    chars = chars
)

fun PswdNiceCharsEntity.toDo() = StrongPswdChars(
    chars = chars
)