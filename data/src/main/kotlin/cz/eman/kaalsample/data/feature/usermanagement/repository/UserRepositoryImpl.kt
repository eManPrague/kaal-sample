package cz.eman.kaalsample.data.feature.usermanagement.repository

import features.usermanagement.model.User
import features.usermanagement.repository.UserRepository
import features.usermanagement.source.UserDataSource


/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository {

    override suspend fun authorizeUser(user: User) = userDataSource.authorizeUser(user)

    override suspend fun registerUser(user: User) = userDataSource.registerUser(user)

}