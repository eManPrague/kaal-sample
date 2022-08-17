package cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper

import cz.eman.kaalsample.domain.feature.usermanagement.model.User
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.UserEntity

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
object UserMapper {

    fun mapUserEntityToUser(entity: UserEntity) =
        User(username = entity.username,
            password = entity.password
        )
}