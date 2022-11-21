package cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper

import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrengthConfig
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.PswdStrengthConfigEntity

fun PswdStrengthConfig.toEntity() = PswdStrengthConfigEntity( invalidChars = invalidChars, pswdLength = pswdLength)

fun PswdStrengthConfigEntity.toDo() = PswdStrengthConfig( invalidChars = invalidChars, pswdLength = pswdLength )