package cz.eman.kaalsample.domain.feature.usermanagement.model

data class PswdStrengthConfig (
    val invalidChars: String,
    val pswdLength: Int
    )