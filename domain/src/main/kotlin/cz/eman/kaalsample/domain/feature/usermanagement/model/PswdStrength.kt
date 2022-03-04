package cz.eman.kaalsample.domain.feature.usermanagement.model

sealed class PswdStrength {

    object Invalid : PswdStrength()

    class ForbidenChar(val fch: String) : PswdStrength()

    object TooShort : PswdStrength()

    object Medium : PswdStrength()

    object OK : PswdStrength()
}