package kamil.demo.kotlin.controller.words

import kamil.demo.kotlin.repository.WordRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/words")
class WordsController(
        private val wordRepository: WordRepository) {

    @GetMapping("/")
    fun getWords() {

    }
}