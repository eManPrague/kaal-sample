package cz.eman.kaalsample.domain

import cz.eman.kaal.domain.ErrorResult

/**
 *
 * @author Roman Holomek <roman.holomek@eman.cz>
 */
data class ApiErrorResult(
    val code: Int,
    val errorMessage: String? = null,
    val apiThrowable: Throwable? = null
) : ErrorResult(errorMessage, apiThrowable)