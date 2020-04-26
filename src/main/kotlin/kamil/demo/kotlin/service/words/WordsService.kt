package kamil.demo.kotlin.service.words

import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.types.WordCategory

interface WordsService {

    /**
     * Method get a word from repository by word text and his category.
     *
     * @param word          text of word
     * @param wordCategory  word category
     * @return searched word or nothing if not found or it's a forbidden word.
     */
    fun getWord(word: String, wordCategory: WordCategory): Word

    /**
     * Method try find word from repository by word in category priorities:
     * 1. noun
     * 2. verb
     * 3. adjective
     *
     * @return searched word or nothing if not found or it's a forbidden word.
     */
    fun getWord(word: String): Word

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
    fun getForbiddenWords(): List<String>

    /**
     * Method add new word to a repository.
     *
     * @return added word
     */
    fun addWord(word: Word): Word

    /**
     * Method add new word to a repository.
     *
     * @return added word
     */
    fun addWord(word: String, wordCategory: WordCategory): Word

    /**
     * Method remove word from a repository.
     */
    fun removeWord(word: Word)

    /**
     * Method get random word by category.
     *
     * @return word in the defined category if exist
     */
    fun getRandomWordByCategory(wordCategory: WordCategory): Word
}