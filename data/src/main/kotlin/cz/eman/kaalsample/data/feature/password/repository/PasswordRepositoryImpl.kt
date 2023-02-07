package cz.eman.kaalsample.data.feature.password.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onError
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaalsample.data.feature.password.datasource.PasswordLocalDataSource
import cz.eman.kaalsample.data.feature.password.datasource.PasswordRemoteDataSource
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PasswordRepository

class PasswordRepositoryImpl(
    private val localDataSource: PasswordLocalDataSource,
    private val remoteDataSource: PasswordRemoteDataSource
) : PasswordRepository {

    override fun getMinPswdLength(): Int {
        val minLength = localDataSource.getPswdMinLength()
        return if (minLength == null) {
            remoteDataSource.getPswdMinLength().onSuccess {
                localDataSource.setPswdMinLength(it)
                return it
            }
            DEFAULT_MIN_LENGTH
        } else {
            minLength
        }
    }

    override fun getMandatoryChars(): Array<Char> {
        TODO("Not yet implemented")
    }

    override fun getForbiddenChars(): Array<Char> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val DEFAULT_MIN_LENGTH = 7
    }
}