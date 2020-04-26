package kamil.demo.kotlin.service.words

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kamil.demo.kotlin.exceptions.ForbiddenWordException
import kamil.demo.kotlin.exceptions.WordNotExistException
import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.types.AppProperties
import kamil.demo.kotlin.types.WordCategory
import org.springframework.stereotype.Service
import java.io.File

@Service
class WordsServiceImpl(
        private val repository: WordRepository,
        private val appProperties: AppProperties) : WordsService {

    val mapper = jacksonObjectMapper()

    override fun getWord(word: String, wordCategory: WordCategory): Word {
        val result = repository.findByWordAndWordCategory(word, wordCategory)

        if (!result.isPresent) {
            throw WordNotExistException("Word \"$word\" with category \"$wordCategory\" not exist.")
        }

        return if (result.stream().anyMatch { w -> getForbiddenWords().contains(w.word) }) {
            throw ForbiddenWordException("Word \"$word\" with category \"$wordCategory\" is forbidden.")
        } else {
            result.get()
        }
    }

    override fun getWord(word: String): Word {
        val result = repository.findByWord(word).toList()

        if (result.isEmpty()) {
            throw WordNotExistException("Word \"$word\" not exist.")
        }

        if (result.stream().anyMatch { w -> getForbiddenWords().contains(w.word) }) {
            throw ForbiddenWordException("Word \"$word\" is forbidden.")
        }

        return result.minBy { w -> w.wordCategory.ordinal }!!
    }

    override fun getAllWords(): List<Word> {
        return repository.findAll()
                .filterNot { getForbiddenWords().contains(it.word) }
                .toList()
    }

    override fun getForbiddenWords(): List<String> {
        val path = appProperties.forbiddenWords
        return if (path.isBlank()) {
            emptyList()
        } else {
            mapper.readValue(File(path))
        }
    }

    override fun addWord(word: Word): Word {
        return repository.save(word)
    }

    override fun addWord(word: String, wordCategory: WordCategory): Word {
        return repository.save(Word(word, wordCategory))
    }

    override fun removeWord(word: Word) {
        repository.delete(word)
    }

    override fun getRandomWordByCategory(wordCategory: WordCategory): Word {
        val words = getAllWords().filter {
            word -> word.wordCategory == wordCategory
        }

        return if (words.isEmpty()) {
            throw WordNotExistException("There are any words in category \"$wordCategory\".")
        } else {
            words.random()
        }
    }
}