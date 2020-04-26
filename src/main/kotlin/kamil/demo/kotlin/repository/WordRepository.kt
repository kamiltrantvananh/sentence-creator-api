package kamil.demo.kotlin.repository

import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.types.WordCategory
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WordRepository : CrudRepository<Word, Long> {

    fun findByWord(word: String): Iterable<Word>

    fun findByWordAndWordCategory(word: String, wordCategory: WordCategory): Optional<Word>
}