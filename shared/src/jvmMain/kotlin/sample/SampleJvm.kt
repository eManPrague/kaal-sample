package sample

import cz.eman.kaal.domain.Result

actual class Sample {
    actual fun checkMe() = 42
}

actual object Platform {
    actual fun name(): String = "JVM"
}
