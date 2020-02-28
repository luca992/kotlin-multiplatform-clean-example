package co.lucaspinazzola.example.domain.utils

expect class Date() {
    constructor(timeInMillis: Long)
    fun getDate(): Int
    fun getMonth(): Int
    fun getFullYear(): Int
    fun getHours(): Int
    fun getMinutes(): Int
    fun getTime(): Long
}

expect operator fun Date.compareTo(otherDate: Date): Int

expect fun Date.toReadableDateString(): String
expect fun Date.toReadableTimeString(): String

fun Date.toReadableDateTimeString() = "${toReadableDateString()} ${toReadableTimeString()}"