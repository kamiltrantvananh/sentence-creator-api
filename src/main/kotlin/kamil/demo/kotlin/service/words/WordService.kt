package kamil.demo.kotlin.service.words

import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.types.WordCategory
import java.util.*

interface WordService {

    fun getWord(word: String, wordCategory: WordCategory): Optional<Word>

    fun getAllWords(): Iterable<Word>

    fun getForbiddenWords(): Iterable<Word>

    fun addWord(word: String, wordCategory: WordCategory)

    fun removeWord(word: String)
}