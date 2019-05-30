package cz.eman.kaalsample.infrastructure.core

import cz.eman.kaal.domain.ErrorResult

/**
 * Example:
 * ```
 *     val error = Result.Error(ErrorCodeResult(code = ErrorCode.NO_ERROR))}
 * ```
 */
data class ErrorCodeResult(val code: ErrorCode,
                           override var message: String? = null,
                           override var throwable: Throwable? = null) : ErrorResult()