package kamil.demo.kotlin.controller.sentences

import kamil.demo.kotlin.model.Sentence
import kamil.demo.kotlin.model.Stats
import kamil.demo.kotlin.model.rest.SentenceBodyRestDto
import kamil.demo.kotlin.model.rest.SentenceRestDto
import kamil.demo.kotlin.service.sentences.SentencesService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Rest controller for sentences.
 */
@RestController
@RequestMapping("/sentences")
class SentencesController(private val sentencesService: SentencesService) {

    @GetMapping("/")
    fun getSentences(): List<SentenceRestDto> {
        return convertToJson(sentencesService.getAllSentences(), false)
    }

    @GetMapping("/{sentenceID}")
    fun getSentence(@PathVariable sentenceID: Long): SentenceRestDto {
        return convertToJson(sentencesService.getSentence(sentenceID), false)
    }

    @GetMapping("/{sentenceID}/yodaTalk")
    fun getSentenceAsYodaTalk(@PathVariable sentenceID: Long): SentenceRestDto {
        return convertToJson(sentencesService.getSentence(sentenceID), true)
    }

    @PostMapping("/generate")
    fun generateSentence(): SentenceRestDto {
        return convertToJson(sentencesService.generateSentence(), false)
    }

    @DeleteMapping("/")
    fun removeAllSentences() {
        return sentencesService.removeAllSentences()
    }

    @DeleteMapping("/{sentenceID}")
    fun removeAllSentences(@PathVariable sentenceID: Long) {
        return sentencesService.removeSentence(sentenceID)
    }

    @GetMapping("/stats")
    fun getStats(): List<Stats> {
        return sentencesService.getStats()
    }

    /**
     * Converter for sentence for specific format in JSON.
     *
     * @param sentence  sentence from crud repository
     * @param asYoda    Yoda talk sentence flag
     */
    fun convertToJson(sentence: Sentence, asYoda: Boolean): SentenceRestDto {
        return SentenceRestDto(
                SentenceBodyRestDto(
                        sentence.id!!,
                        sentencesService.createSentence(sentence, asYoda),
                        sentence.showDisplayCount!!,
                        sentence.timestamp))
    }

    /**
     * Converter for sentences for specific format in JSON.
     *
     * @param sentences list of sentences from crud repository
     * @param asYoda    Yoda talk sentence flag
     */
    fun convertToJson(sentences: List<Sentence>, asYoda: Boolean): List<SentenceRestDto> {
        return sentences.map { sentence -> convertToJson(sentence, asYoda) }.toList()
    }
}