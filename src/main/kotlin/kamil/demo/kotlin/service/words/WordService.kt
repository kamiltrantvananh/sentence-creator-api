package kamil.demo.kotlin.service.words

import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.types.WordCategory
import java.util.*

interface WordService {

    /**
     * Method get a word from repository by word text and hiw category.
     *
     * @param word          text of word
     * @param wordCategory  word category
     * @return searched word or nothing if not found or it's a forbidden word.
     */
    fun getWord(word: String, wordCategory: WordCategory): Optional<Word>

    /**
     * Method get all words from repository.
     *
     * @return list of words without forbidden words.
     */
    fun getAllWords(): List<Word>

    /**
     * Method get all forbidden words from file defined on path in 'application.properties' file.
     *
     * @return list of forbidden words
     */
    fun getForbiddenWords(): List<Word>

    /**
     * Method add new word to a repository.
     *
     * @return added word
     */
    fun addWord(word: Word): Word

    /**
     * Method remove word from a repository.
     */
    fun removeWord(word: Word)
}