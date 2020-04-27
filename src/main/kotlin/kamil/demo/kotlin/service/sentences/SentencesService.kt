package kamil.demo.kotlin.service.sentences

import kamil.demo.kotlin.exceptions.SentenceNotExistException
import kamil.demo.kotlin.exceptions.WordNotExistException
import kamil.demo.kotlin.model.Sentence
import kamil.demo.kotlin.model.Stats

/**
 * Service for sentences.
 */
interface SentencesService {

    /**
     * Get all sentences.
     *
     * @return list of Sentences
     */
    fun getAllSentences(): List<Sentence>

    /**
     * Get sentence by his ID.
     *
     * @throws SentenceNotExistException if sentence not exist
     * @return sentence
     */
    fun getSentence(id: Long): Sentence

    /**
     * Randomly generate sentence from words and their categories.
     *
     * @throws WordNotExistException if word in category not exist
     * @return generated sentence
     */
    fun generateSentence(): Sentence

    /**
     * Remove sentence by his ID.
     */
    fun removeSentence(id: Long)

    /**
     * Remove all sentences.
     */
    fun removeAllSentences()

    /**
     * Get stats of duplicated sentences.
     */
    fun getStats(): List<Stats>

    /**
     * Create sentence from words.
     *
     * @return sentence as text
     */
    fun createSentence(sentence: Sentence, asYoda: Boolean): String
}