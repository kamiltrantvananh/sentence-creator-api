package kamil.demo.kotlin.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import kamil.demo.kotlin.exceptions.ForbiddenWordException
import kamil.demo.kotlin.exceptions.WordNotExistException
import kamil.demo.kotlin.matchers.WordMatcher
import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.service.words.WordsService
import kamil.demo.kotlin.types.AppProperties
import kamil.demo.kotlin.types.WordCategory.ADJECTIVE
import kamil.demo.kotlin.types.WordCategory.NOUN
import kamil.demo.kotlin.types.WordCategory.VERB
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@SpringBootTest
@ExtendWith(SpringExtension::class)
class WordsServiceTest {

    @Autowired
    lateinit var service: WordsService

    @MockkBean
    lateinit var repository: WordRepository

    @MockkBean
    lateinit var properties: AppProperties

    private final val forbiddenWordPath = WordsServiceTest::class.java.getResource("/forbidden-words.json").path
    val wordOne = Word("one", NOUN, 1L)
    val wordTwo = Word("two", VERB, 2L)
    val forbidden = Word("FUCKER", NOUN, 3L)

    @Test
    fun `getWord by word and category return Word`() {
        every { properties.forbiddenWords } returns forbiddenWordPath
        every { repository.findByWordAndWordCategory("one", NOUN) } returns Optional.of(wordOne)

        val result = service.getWord("one", NOUN)

        assertThat(result, WordMatcher(`is`("one"), `is`(NOUN)))
    }

    @Test
    fun `forbidden getWord by word and category throws ForbiddenWordException`() {
        every { properties.forbiddenWords } returns forbiddenWordPath
        every { repository.findByWordAndWordCategory("FUCKER", NOUN) } returns
                Optional.of(forbidden)

        assertThrows<ForbiddenWordException> {
            service.getWord("FUCKER", NOUN)
        }
    }

    @Test
    fun `getWord by word return Word`() {
        every { properties.forbiddenWords } returns forbiddenWordPath
        every { repository.findByWord("one") } returns listOf(wordOne, wordTwo)

        val result = service.getWord("one")

        assertThat(result, WordMatcher(`is`("one"), `is`(NOUN)))
    }

    @Test
    fun `getWord by word rthrows WordNotExistExceptio`() {
        every { properties.forbiddenWords } returns forbiddenWordPath
        every { repository.findByWord("one") } returns emptyList()

        assertThrows<WordNotExistException> {
            service.getWord("one")
        }
    }

    @Test
    fun `forbidden getWord by word throws ForbiddenWordException`() {
        every { properties.forbiddenWords } returns forbiddenWordPath
        every { repository.findByWord("FUCKER") } returns listOf(forbidden)

        assertThrows<ForbiddenWordException> {
            service.getWord("FUCKER")
        }
    }

    @Test
    fun `getWord throws WordNotExistException`() {
        every { properties.forbiddenWords } returns forbiddenWordPath
        every { repository.findByWordAndWordCategory("one", NOUN) } returns Optional.empty()

        assertThrows<WordNotExistException> {
            service.getWord("one", NOUN)
        }
    }

    @Test
    fun `getForbiddenWords return list of Word`() {
        every { properties.forbiddenWords } returns forbiddenWordPath

        val result = service.getForbiddenWords()

        assertThat(result, containsInAnyOrder("FUCK", "FUCKER", "COWARD"))
    }

    @Test
    fun `getForbiddenWords return empty`() {
        every { properties.forbiddenWords } returns ""

        val result = service.getForbiddenWords()

        assertThat(result, `is`(emptyList()))
    }

    @Test
    fun `getAllWords return list of Words without forbidden`() {
        every { repository.findAll() } returns listOf(wordOne, forbidden)
        every { properties.forbiddenWords } returns forbiddenWordPath

        val result = service.getAllWords()

        assertThat(result, containsInAnyOrder(WordMatcher(`is`("one"), `is`(NOUN))))
    }

    @Test
    fun `addWord return Word`() {
        every { repository.save(wordOne) } returns wordOne

        val result = service.addWord(wordOne)

        verify { repository.save(wordOne) }
        assertThat(result, WordMatcher(`is`("one"), `is`(NOUN)))
    }

    @Test
    fun `addWord by text and category return Word`() {
        every { repository.save(wordOne) } returns wordOne

        val result = service.addWord(wordOne.word, wordOne.wordCategory)

        verify { repository.save(wordOne) }
        assertThat(result, WordMatcher(`is`("one"), `is`(NOUN)))
    }

    @Test
    fun `removeWord return successful`() {
        every { repository.delete(wordOne) } returns Unit

        service.removeWord(wordOne)

        verify { repository.delete(wordOne) }
    }

    @Test
    fun `getRandomWordByCategory return Word`() {
        every { properties.forbiddenWords } returns ""
        every { repository.findAll() } returns listOf(wordOne, wordTwo)

        val word = service.getRandomWordByCategory(NOUN)

        assertThat(word, WordMatcher(`is`("one"), `is`(NOUN)))
    }

    @Test
    fun `getRandomWordByCategory throws WordNotExistException`() {
        every { properties.forbiddenWords } returns ""
        every { repository.findAll() } returns listOf(wordOne, wordTwo)

        assertThrows<WordNotExistException> {
            service.getRandomWordByCategory(ADJECTIVE)
        }
    }
}