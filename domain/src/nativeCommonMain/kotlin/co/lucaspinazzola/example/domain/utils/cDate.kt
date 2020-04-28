package co.lucaspinazzola.example.domain.utils

actual class Date {
    private val calendar: Long

    actual constructor() {
        calendar = 0
    }

    actual constructor(timeInMillis: Long) {
        calendar = timeInMillis
    }

    val date: Long get() = calendar

    actual fun getDate() = calendar.toInt()
    actual fun getMonth() = calendar.toInt()
    actual fun getFullYear() = calendar.toInt()
    actual fun getHours() = calendar.toInt()
    actual fun getMinutes() = calendar.toInt()
    actual fun getTime(): Long = calendar

    override fun equals(other: Any?): Boolean = other is Date && other.getTime() == getTime()
}

actual operator fun Date.compareTo(otherDate: Date): Int = date.compareTo(otherDate.date)


actual fun Date.toReadableDateString() = ""
actual fun Date.toReadableTimeString() = ""
