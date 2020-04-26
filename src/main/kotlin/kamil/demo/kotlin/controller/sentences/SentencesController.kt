package kamil.demo.kotlin.controller.sentences

import kamil.demo.kotlin.model.Sentence
import kamil.demo.kotlin.model.rest.SentenceBodyRestDto
import kamil.demo.kotlin.model.rest.SentenceRestDto
import kamil.demo.kotlin.service.sentences.SentencesService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/sentences")
class SentencesController(
        private val sentencesService: SentencesService
) {

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

    fun convertToJson(sentence: Sentence, asYoda: Boolean): SentenceRestDto {

        val text = if (asYoda) {
            "${sentence.adjective} ${sentence.noun} ${sentence.verb}"
        } else {
            "${sentence.noun} ${sentence.verb} ${sentence.adjective}"
        }
        return SentenceRestDto(
                SentenceBodyRestDto(sentence.id!!, text, sentence.showDisplayCount!!, sentence.timestamp))
    }

    fun convertToJson(sentences: List<Sentence>, asYoda: Boolean): List<SentenceRestDto> {
        return sentences.map { sentence -> convertToJson(sentence, asYoda) }.toList()
    }
}