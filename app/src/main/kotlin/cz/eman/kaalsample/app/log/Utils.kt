package cz.eman.kaalsample.app.log

import android.util.Log
import io.sentry.event.Event

/**
 * TODO CLASS_DESCRIPTION
 *
 * @author eMan s.r.o. (info@eman.cz)
 */

fun mapPriorityToLevel(priority: Int) = when (priority) {
    Log.VERBOSE -> Event.Level.DEBUG
    Log.DEBUG -> Event.Level.DEBUG
    Log.INFO -> Event.Level.INFO
    Log.WARN -> Event.Level.WARNING
    Log.ERROR -> Event.Level.ERROR
    Log.ASSERT -> Event.Level.FATAL
    else -> Event.Level.FATAL
}
