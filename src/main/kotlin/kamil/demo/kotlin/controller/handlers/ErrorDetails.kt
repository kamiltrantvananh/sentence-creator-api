package kamil.demo.kotlin.controller.handlers

import java.time.LocalDateTime

data class ErrorsDetails(
        val time: LocalDateTime,
        val message: String,
        val details: String)