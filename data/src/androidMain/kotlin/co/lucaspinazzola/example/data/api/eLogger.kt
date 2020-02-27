package co.lucaspinazzola.example.data.api

import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.MessageLengthLimitingLogger
import io.ktor.client.features.logging.SIMPLE

actual val eLogger: Logger = MessageLengthLimitingLogger(delegate = Logger.Companion.SIMPLE)