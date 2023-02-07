package cz.eman.kaalsample.domain.feature.usermanagement.repository

interface PasswordRepository {

    fun getMinPswdLength(): Int

    fun getMandatoryChars(): Array<Char>

    fun getForbiddenChars(): Array<Char>
}