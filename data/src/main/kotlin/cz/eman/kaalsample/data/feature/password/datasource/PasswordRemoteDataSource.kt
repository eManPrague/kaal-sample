package cz.eman.kaalsample.data.feature.password.datasource

interface PasswordRemoteDataSource {

    fun getPswdMinLength(): cz.eman.kaal.domain.result.Result<Int>
}