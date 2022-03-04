package cz.eman.kaalsample.data.feature.security.repository

import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository

class SecurityRepositoryImpl(

) : SecurityRepository {


    override fun getFch(): String {
        return " "
    }

    override fun getSch(): String {
        return "@#$"
    }
}