package kamil.demo.kotlin.service.words

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.types.AppProperties
import kamil.demo.kotlin.types.WordCategory
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class WordServiceImpl(
        private val repository: WordRepository,
        private val appProperties: AppProperties) : WordService {

    val mapper = jacksonObjectMapper()

    override fun getWord(word: String, wordCategory: WordCategory): Optional<Word> {
        val result = repository.findByWordAndWordCategory(word, wordCategory)
        return if (getForbiddenWords().contains(result)) Optional.empty() else Optional.ofNullable(result)
    }

    override fun getAllWords(): List<Word> {
        return repository.findAll()
                .filterNot { getForbiddenWords().contains(it) }
                .toList()
    }

    override fun getForbiddenWords(): List<Word> {
        val file = File(appProperties.forbiddenWords)
        return mapper.readValue(file)
    }

    override fun addWord(word: Word): Word {
        return repository.save(word)
    }

    override fun removeWord(word: Word) {
        repository.delete(word)
    }
}