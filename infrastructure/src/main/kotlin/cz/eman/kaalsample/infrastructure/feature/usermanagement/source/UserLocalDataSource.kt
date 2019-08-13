package cz.eman.kaalsample.infrastructure.feature.usermanagement.source

import cz.eman.kaal.domain.Result
import cz.eman.kaalsample.infrastructure.core.ErrorCode
import cz.eman.kaalsample.infrastructure.core.ErrorCodeResult
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.dao.UserDao
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.UserEntity
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.UserMapper
import features.usermanagement.model.User
import features.usermanagement.source.UserDataSource

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
class UserLocalDataSource(private val userDao: UserDao) : UserDataSource {

    override suspend fun authorizeUser(user: User): Result<User> {
        val entity = userDao.findUser(username = user.username, password = user.password)
        return entity?.let {
            Result.Success(data = UserMapper.mapUserEntityToUser(entity))
        } ?: run {
            Result.Error(
                    ErrorCodeResult(
                            ErrorCode.INVALID_USER_CREDENTIALS,
                            message = "Invalid username or password"
                    )
            )
        }
    }

    override suspend fun registerUser(user: User): Result<User> {
        val entity = userDao.getUserByName(user.username)
        return entity?.let {
            Result.Error(
                    ErrorCodeResult(
                            ErrorCode.USER_ALREADY_EXIST,
                            message = "User already exist"
                    )
            )
        } ?: run {
            val userEntity = UserEntity(user.username, user.password)
            userDao.insert(userEntity)
            Result.Success(data = UserMapper.mapUserEntityToUser(userEntity))
        }
    }
}