package cz.eman.gruntsample.app.log

import android.util.Log
import io.sentry.Sentry
import io.sentry.event.EventBuilder
import io.sentry.event.interfaces.ExceptionInterface
import timber.log.Timber

/**
 * Release [LogTree] implementation for Timber library<br></br>
 * <br></br>
 * Info or higher are logged to Sentry<br></br>
 */
class LogTree : Timber.Tree() {

    override fun isLoggable(priority: Int) = priority >= Log.INFO

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
