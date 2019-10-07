package cz.eman.kaalsample.infrastructure.feature.usermanagement.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.User
import cz.eman.kaalsample.domain.feature.usermanagement.source.UserDataSource
import cz.eman.kaalsample.infrastructure.core.MovieErrorCode
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao.UserDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.UserEntity
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.UserMapper

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class UserLocalDataSource(private val userDao: UserDao) : UserDataSource {

    override suspend fun authorizeUser(user: User): Result<User> {
        val entity = userDao.findUser(username = user.username, password = user.password)
        return if (entity != null) {
            Result.success(data = UserMapper.mapUserEntityToUser(entity))
        } else {
            Result.error(
                errorCode = MovieErrorCode.INVALID_USER_CREDENTIALS,
                message = "Invalid username or password"
            )
        }
    }

    override suspend fun registerUser(user: User): Result<User> {
        val entity = userDao.selectUserByUsername(user.username)
        return if (entity != null) {
            Result.error(
                errorCode = MovieErrorCode.USER_ALREADY_EXIST,
                message = "User already exist"
            )

        } else {
            val userEntity = UserEntity(user.username, user.password)
            userDao.insert(userEntity)
            Result.Success(data = UserMapper.mapUserEntityToUser(userEntity))
        }
    }
}