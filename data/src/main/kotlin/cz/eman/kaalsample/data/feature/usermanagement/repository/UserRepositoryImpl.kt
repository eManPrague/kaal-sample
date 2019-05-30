package cz.eman.kaalsample.data.feature.usermanagement.repository

import cz.eman.kaalsample.domain.feature.usermanagement.model.User
import cz.eman.kaalsample.domain.feature.usermanagement.repository.UserRepository
import cz.eman.kaalsample.domain.feature.usermanagement.source.UserDataSource

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository {

    override suspend fun authorizeUser(user: User) = userDataSource.authorizeUser(user)

    override suspend fun registerUser(user: User) = userDataSource.registerUser(user)

}