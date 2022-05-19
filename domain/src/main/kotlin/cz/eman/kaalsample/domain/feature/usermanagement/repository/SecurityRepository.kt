package cz.eman.kaalsample.domain.feature.usermanagement.repository

interface SecurityRepository {

    fun getSepcialChars(): String

    fun getSuggestedCharacters(): List<Char>

    fun getForbiddenCharacters(): List<Char>
}
