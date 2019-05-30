package cz.eman.kaalsample.app.log

import android.util.Log
import io.sentry.Sentry
import io.sentry.event.EventBuilder
import io.sentry.event.interfaces.ExceptionInterface
import timber.log.Timber

/**
 * Debug [LogTree] implementation for Timber library<br></br>
 * <br></br>
 * All logs are written in Android [Log]<br></br>
 * Error or higher logs are thrown as [UnsupportedOperationException], others are sent to Sentry
 */
class LogTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, error: Throwable?) {
        super.log(priority, tag, message, error)
        if (priority >= Log.ERROR) { // throw error or higher exceptions
            throw UnsupportedOperationException(message, error)
        }

        Sentry.capture(
            EventBuilder().apply {
                withLevel(mapPriorityToLevel(priority))
                withMessage(tag?.plus(": ")?.plus(message) ?: message)
                withSentryInterface(ExceptionInterface(error))
            }
        )
    }
}
