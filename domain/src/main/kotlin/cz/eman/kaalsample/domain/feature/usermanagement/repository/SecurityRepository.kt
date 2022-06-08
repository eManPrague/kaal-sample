package cz.eman.kaalsample.domain.feature.usermanagement.repository

interface SecurityRepository {

    fun getPswdUnssuportedChars(): String
}