package kamil.demo.kotlin.controller.handlers

import java.time.LocalDateTime

/**
 * Data class for error details.
 */
data class ErrorsDetails(
        val time: LocalDateTime,
        val message: String,
        val details: String)