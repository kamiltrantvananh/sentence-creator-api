package kamil.demo.kotlin.repository

import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.types.WordCategory
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Crud repository for words.
 */
interface WordRepository : CrudRepository<Word, Long> {

    /**
     * Find words by text.
     *
     * @return list of words
     */
    fun findByWord(word: String): Iterable<Word>

    /**
     * Find words by text and category.
     *
     * @return optional with/without word
     */
    fun findByWordAndWordCategory(word: String, wordCategory: WordCategory): Optional<Word>
}