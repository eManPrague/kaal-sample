package cz.eman.kaalsample.domain.feature.usermanagement.repository

interface SecurityRepository {

    fun getFch(): String

    fun getSch(): String

}