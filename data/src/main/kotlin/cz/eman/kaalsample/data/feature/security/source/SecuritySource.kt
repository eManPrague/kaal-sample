package cz.eman.kaalsample.data.feature.security.source

interface SecuritySource {

    fun getPswdUnssuportedChars(): String

    fun savePswdUnsupportedChars(data: String)
}