package cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper

import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.PasswordForbiddenCharsEntity

fun PasswordText.toEntity() = PasswordForbiddenCharsEntity(
    id=0,
    chars = chars
)

fun PasswordForbiddenCharsEntity.toDTO () = PasswordText (chars=chars)