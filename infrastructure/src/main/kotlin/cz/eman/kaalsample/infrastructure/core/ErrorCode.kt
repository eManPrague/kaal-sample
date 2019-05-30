package cz.eman.kaalsample.infrastructure.core

/**
 * Enum defines all error codes across the application
 *
 * Format number value: LAYER_FEATURE_CODE
 *
 * @author [Filip Šmíd](mailto:filip.smid@eman.cz)
 */
enum class ErrorCode(val value: Int) {

    NO_ERROR(0),
    NO_MOVIES_IN_CACHE(1),
    INVALID_USER_CREDENTIALS(2),
    USER_ALREADY_EXIST(3)

}
