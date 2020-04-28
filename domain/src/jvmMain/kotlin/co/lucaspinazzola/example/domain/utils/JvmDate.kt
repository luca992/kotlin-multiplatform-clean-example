package co.lucaspinazzola.example.domain.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

actual class Date {
    private val calendar: Calendar

    actual constructor() {
        calendar = Calendar.getInstance()
    }

    constructor(date: java.util.Date?) {
        calendar = Calendar.getInstance().apply {
            if (date!=null) {
                time = date
            }
        }
    }

    actual constructor(timeInMillis: Long) {
        calendar = Calendar.getInstance().apply {
            setTimeInMillis(timeInMillis)
        }
    }

    val date: java.util.Date get() = calendar.time

    actual fun getDate() = calendar[DAY_OF_MONTH]
    actual fun getMonth() = calendar[MONTH]
    actual fun getFullYear() = calendar[YEAR]
    actual fun getHours() = calendar[HOUR_OF_DAY]
    actual fun getMinutes() = calendar[MINUTE]
    actual fun getTime(): Long = calendar.timeInMillis

    override fun equals(other: Any?): Boolean = other is Date && other.calendar.time == calendar.time
}

actual operator fun Date.compareTo(otherDate: Date): Int = date.compareTo(otherDate.date)

val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
val readableDateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
val readableTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

actual fun Date.toReadableDateString() = readableDateFormat.format(date)
actual fun Date.toReadableTimeString() = readableTimeFormat.format(date)
