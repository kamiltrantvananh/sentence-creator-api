package kamil.demo.kotlin.model.rest

import java.time.LocalDateTime

class SentenceBodyRestDto(
        val id: Long,
        val text: String,
        val showDisplayedCount: Int,
        val created: LocalDateTime? = null
) {
    override fun toString(): String {
        return "SentenceBodyRestDto(id=$id, text='$text', showDisplayedCount=$showDisplayedCount, created=$created)"
    }
}