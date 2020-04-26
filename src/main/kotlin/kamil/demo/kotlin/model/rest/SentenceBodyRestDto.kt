package kamil.demo.kotlin.model.rest

import java.time.LocalDateTime

class SentenceBodyRestDto(
        val id: Long,
        val text: String,
        val showDisplayedCount: Int,
        val created: LocalDateTime? = null
)