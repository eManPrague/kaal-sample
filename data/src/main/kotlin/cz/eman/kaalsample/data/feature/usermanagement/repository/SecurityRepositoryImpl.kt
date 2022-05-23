package cz.eman.kaalsample.data.feature.usermanagement.repository

import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository

class SecurityRepositoryImpl : SecurityRepository {
    override fun getInvalidCharacters(): List<Char> = listOf('|','~','*')

    override fun getMinCharactersCount(): Int = 7

    override fun getStrongCharactersCount(): Int = 10
}