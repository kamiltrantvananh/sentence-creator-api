package kamil.demo.kotlin.service.words

import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.types.WordCategory
import org.springframework.stereotype.Service
import java.util.*

@Service
class WordServiceImpl(private val repository: WordRepository) : WordService {

    override fun getWord(word: String, wordCategory: WordCategory): Optional<Word> {
        return Optional.ofNullable(repository.findByWordAndWordCategory(word, wordCategory))
    }

    override fun getAllWords(): Iterable<Word> {
        TODO("not implemented")
    }

    override fun getForbiddenWords(): Iterable<Word> {
        TODO("not implemented")
    }

    override fun addWord(word: String, wordCategory: WordCategory) {
        TODO("not implemented")
    }

    override fun removeWord(word: String) {
        TODO("not implemented")
    }
}