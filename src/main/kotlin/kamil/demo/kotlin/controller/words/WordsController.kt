package kamil.demo.kotlin.controller.words

import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.model.rest.WordBodyRestDto
import kamil.demo.kotlin.model.rest.WordRestDto
import kamil.demo.kotlin.service.words.WordsService
import kamil.demo.kotlin.types.WordCategory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/words")
class WordsController(private val wordsService: WordsService) {

    @GetMapping("/{word}/{wordCategory}")
    fun getWord(@PathVariable word: String, @PathVariable wordCategory: WordCategory): WordRestDto {
        return convertToJson(wordsService.getWord(word, wordCategory))
    }

    @GetMapping("/{word}")
    fun getWord(@PathVariable word: String): WordRestDto {
        return convertToJson(wordsService.getWord(word))
    }

    @GetMapping("/")
    fun getWords(): List<WordRestDto> {
        return convertToJson(wordsService.getAllWords())
    }

    @PutMapping("/{word}")
    fun putWord(@PathVariable word: String, @RequestBody body: WordRestDto): WordRestDto {
        val wordCategory = body.word.wordCategory
        return convertToJson(wordsService.addWord(word, wordCategory))
    }

    fun convertToJson(word: Word): WordRestDto {
        return WordRestDto(
                WordBodyRestDto(word.word, word.wordCategory))
    }

    fun convertToJson(words: List<Word>): List<WordRestDto> {
        return words.map { word -> convertToJson(word) }.toList()
    }
}