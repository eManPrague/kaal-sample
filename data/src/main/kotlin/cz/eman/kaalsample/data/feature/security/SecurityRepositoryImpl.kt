package cz.eman.kaalsample.data.feature.security

import cz.eman.kaalsample.data.feature.security.source.SecuritySource
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository

class SecurityRepositoryImpl(
    private val localDataSource: SecuritySource,
    private val remoteDataSource: SecuritySource
) : SecurityRepository {

    override fun getPswdUnssuportedChars(): String {
        val localData = localDataSource.getPswdUnssuportedChars()
        return if (localData.isEmpty()) {
            val remoteData = remoteDataSource.getPswdUnssuportedChars()
            localDataSource.savePswdUnsupportedChars(remoteData)
            remoteData
        } else {
            localData
        }
    }
}