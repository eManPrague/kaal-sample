package sample

actual class Sample {
    actual fun checkMe() = 7
}

actual object Platform {
    actual fun name(): String = "iOS"
}

fun foo(bar: List<String>) = listOf<String>()