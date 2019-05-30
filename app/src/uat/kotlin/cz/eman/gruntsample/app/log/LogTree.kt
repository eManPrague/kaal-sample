package cz.eman.gruntsample.app.log

import io.sentry.Sentry
import io.sentry.event.EventBuilder
import io.sentry.event.interfaces.ExceptionInterface
import timber.log.Timber

/**
 * Uat [LogTree] implementation for Timber library<br></br>
 * <br></br>
 * All logs are logged to Sentry<br></br>
 */
class LogTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, error: Throwable?) {
        Sentry.capture(
            EventBuilder().apply {
                withLevel(mapPriorityToLevel(priority))
                withMessage(tag?.plus(": ")?.plus(message) ?: message)
                withSentryInterface(ExceptionInterface(error))
            }
        )
    }
}
