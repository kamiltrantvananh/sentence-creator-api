package kamil.demo.kotlin.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import kamil.demo.kotlin.exceptions.SentenceNotExistException
import kamil.demo.kotlin.matchers.SentenceMatcher
import kamil.demo.kotlin.matchers.StatsMatcher
import kamil.demo.kotlin.model.Sentence
import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.repository.SentenceRepository
import kamil.demo.kotlin.service.sentences.SentencesService
import kamil.demo.kotlin.service.words.WordsService
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
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@ExtendWith(SpringExtension::class)
class SentencesServiceTest {

    @Autowired
    lateinit var service: SentencesService

    @MockkBean
    lateinit var repository: SentenceRepository

    @MockkBean
    lateinit var wordsService: WordsService

    val id: Long = 0
    val sentenceOne = Sentence("one", "hits", "two", 0)
    val sentenceTwo = Sentence("data", "are", "good")
    val sentenceThree = Sentence("one", "hits", "two", 0)

    @Test
    fun `getAllSentences return list of Sentence`() {
        val now = LocalDateTime.now()
        every { repository.findAll() } returns listOf(sentenceOne, sentenceTwo)
        every { repository.save(sentenceOne) } returns
                Sentence("one", "hits", "two", 1, now)
        every { repository.save(sentenceTwo) } returns
                Sentence("data", "are", "good", 1, now)

        val sentences = service.getAllSentences()

        assertThat(sentences, containsInAnyOrder(
                SentenceMatcher(`is`("one"), `is`("hits"), `is`("two"), `is`(1), `is`(now)),
                SentenceMatcher(`is`("data"), `is`("are"), `is`("good"), `is`(1), `is`(now))))
    }

    @Test
    fun `getSentence return Sentence`() {
        val now = LocalDateTime.now()
        every { repository.findById(id) } returns Optional.of(sentenceOne)
        sentenceOne.showDisplayCount?.inc()
        every { repository.save(sentenceOne) } returns sentenceOne

        val sentence = service.getSentence(id)

        assertThat(sentence, SentenceMatcher(`is`("one"), `is`("hits"), `is`("two"), `is`(1), `is`(now)))
    }

    @Test
    fun `getSentence throw exception`() {
        every { repository.findById(id) } returns Optional.empty()

        assertThrows<SentenceNotExistException> {
            service.getSentence(id)
        }
    }

    @Test
    fun `generateSentence return Sentence`() {
        val now = LocalDateTime.now()
        every { wordsService.getRandomWordByCategory(NOUN) } returns Word("n", NOUN)
        every { wordsService.getRandomWordByCategory(VERB) } returns Word("v", VERB)
        every { wordsService.getRandomWordByCategory(ADJECTIVE) } returns Word("o", ADJECTIVE)
        val sentence = Sentence("n", "v", "o", 1)
        every { repository.save(sentence) } returns sentence

        val result = service.generateSentence()

        assertThat(result, SentenceMatcher(`is`("n"), `is`("v"), `is`("o"), `is`(1), `is`(now)))
    }

    @Test
    fun `getStats return list of Stats`() {
        val now = LocalDateTime.now()
        every { repository.findAll() } returns listOf(sentenceOne, sentenceTwo, sentenceThree)
        every { repository.save(sentenceOne) } returns
                Sentence("one", "hits", "two", 1, now, 1L)
        every { repository.save(sentenceTwo) } returns
                Sentence("data", "are", "good", 1, now, 2L)
        every { repository.save(sentenceThree) } returns
                Sentence("one", "hits", "two", 1, now, 3L)

        val stats = service.getStats()

        assertThat(stats, containsInAnyOrder(
                StatsMatcher(containsInAnyOrder(1L, 3L), `is`("one hits two")),
                StatsMatcher(containsInAnyOrder(2L), `is`("data are good"))
        ))
    }

    @Test
    fun `removeSentences return success`() {
        every { repository.deleteAll() } returns Unit

        service.removeAllSentences()

        verify { repository.deleteAll() }
    }

    @Test
    fun `removeSentence return success`() {
        every { repository.deleteById(1L) } returns Unit

        service.removeSentence(1L)

        verify { repository.deleteById(1L) }
    }
}