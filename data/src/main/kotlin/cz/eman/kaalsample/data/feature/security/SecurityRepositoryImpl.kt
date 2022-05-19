package cz.eman.kaalsample.data.feature.security

import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository

class SecurityRepositoryImpl : SecurityRepository {
    override fun getSepcialChars(): String {
        return "#$~"
    }

    override fun getSuggestedCharacters(): List<Char> {
        return listOf('!', '@', '#', '?', '(', ')', '[', ']', '{', '}', '"', '$', '%',
            '&', '\'', '*', '+', '-', '.', ':', ';', '=', '\\', '^', '_', '`', '|', '~')
    }

    override fun getForbiddenCharacters(): List<Char> {
        return listOf('/', ',', ' ', '<', '>')
    }
}