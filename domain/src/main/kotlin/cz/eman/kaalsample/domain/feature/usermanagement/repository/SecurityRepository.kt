package cz.eman.kaalsample.domain.feature.usermanagement.repository

interface SecurityRepository {

    fun getInvalidCharacters(): List<Char>

    fun getMinCharactersCount(): Int = 7

    fun getStrongCharactersCount(): Int = 10

}