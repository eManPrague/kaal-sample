package cz.eman.kaalsample.data.feature.password.datasource

interface PasswordLocalDataSource {

    fun setPswdMinLength(minLength: Int)

    fun getPswdMinLength(): Int?
}