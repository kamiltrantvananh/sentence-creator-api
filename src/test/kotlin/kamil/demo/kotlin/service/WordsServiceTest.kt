package kamil.demo.kotlin.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import kamil.demo.kotlin.matchers.WordMatcher
import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.service.words.WordService
import kamil.demo.kotlin.types.AppProperties
import kamil.demo.kotlin.types.WordCategory
import kamil.demo.kotlin.types.WordCategory.NOUN
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class WordsServiceTest {

    @Autowired
    lateinit var wordService: WordService

    @MockkBean
    lateinit var wordRepository: WordRepository

    @MockkBean
    lateinit var appProperties: AppProperties

    @Test
    fun `getWord return Optional Word`() {
        every { appProperties.forbiddenWords } returns "src/main/resources/forbidden-words.json"
        every { wordRepository.findByWordAndWordCategory("one", NOUN) } returns Word("one", NOUN, 1L)
        val result = wordService.getWord("one", NOUN)

        assertThat(result.isPresent, `is`(true))
        assertThat(result.get(), WordMatcher(`is`("one"), `is`(NOUN)))
    }

    @Test
    fun `forbidden getWord return Optional empty`() {
        val wordOne =
        every { appProperties.forbiddenWords } returns "src/main/resources/forbidden-words.json"
        every { wordRepository.findByWordAndWordCategory("FUCKER", NOUN) } returns
                Word("FUCKER", NOUN, 2L)
        val result = wordService.getWord("FUCKER", NOUN)

        assertFalse(result.isPresent)
    }

    @Test
    fun `getWord return Optional empty`() {
        every { appProperties.forbiddenWords } returns "src/main/resources/forbidden-words.json"
        every { wordRepository.findByWordAndWordCategory("one", NOUN) } returns null
        val result = wordService.getWord("one", NOUN)

        assertFalse(result.isPresent)
    }

    @Test
    fun `getForbiddenWords return list of Word`() {
        every { appProperties.forbiddenWords } returns "src/main/resources/forbidden-words.json"
        val result = wordService.getForbiddenWords()

        assertThat(result, containsInAnyOrder(
                WordMatcher(`is`("FUCK"), `is`(WordCategory.VERB)),
                WordMatcher(`is`("FUCKER"), `is`(NOUN)),
                WordMatcher(`is`("COWARD"), `is`(WordCategory.OBJECTIVE))
        ))
    }

    @Test
    fun `getAllWords return list of Words without forbidden`() {
        every { wordRepository.findAll() } returns listOf(
                Word("one", NOUN, 1L), Word("FUCK", WordCategory.VERB, 2L))
        every { appProperties.forbiddenWords } returns "src/main/resources/forbidden-words.json"
        val result = wordService.getAllWords()

        assertThat(result, containsInAnyOrder(WordMatcher(`is`("one"), `is`(NOUN))))
    }

    @Test
    fun `addWord return Word`() {
        val word = Word("one", NOUN)
        every { wordRepository.save(word) } returns word
        val result = wordService.addWord(word)

        verify { wordRepository.save(word) }
        assertThat(result, WordMatcher(`is`("one"), `is`(NOUN)))
    }

    @Test
    fun `removeWord return successful`() {
        val word = Word("one", NOUN)
        every { wordRepository.delete(word) } returns Unit
        wordService.removeWord(word)

        verify { wordRepository.delete(word) }
    }
}