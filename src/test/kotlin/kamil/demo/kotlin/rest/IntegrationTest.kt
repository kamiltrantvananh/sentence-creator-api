package kamil.demo.kotlin.rest

import kamil.demo.kotlin.model.Sentence
import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.model.rest.WordBodyRestDto
import kamil.demo.kotlin.model.rest.WordRestDto
import kamil.demo.kotlin.repository.SentenceRepository
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.types.WordCategory
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest(
        @Autowired val restTemplate: TestRestTemplate,
        @Autowired val wordRepository: WordRepository,
        @Autowired val sentenceRepository: SentenceRepository
) {

    val defaultTimestamp: LocalDateTime = LocalDateTime.now()
    val idSentenceOne = 1L
    val idSentenceTwo = 2L

    @BeforeAll
    fun setup() {
        wordRepository.save(Word("one", WordCategory.NOUN))
        wordRepository.save(Word("two", WordCategory.VERB))
        wordRepository.save(Word("three", WordCategory.ADJECTIVE))

        sentenceRepository.save(Sentence("one", "two", "three", 3, defaultTimestamp, idSentenceOne))
        sentenceRepository.save(Sentence("Yoda", "talk", "normal", 1, defaultTimestamp.minusDays(2), idSentenceTwo))
    }

    @Test
    fun `getWords return list of Word`() {
        val entity = restTemplate.getForEntity<String>("/api/v1/words/")

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        assertThat(entity.body, `is`("[ {\r\n" +
                "  \"word\" : {\r\n" +
                "    \"word\" : \"one\",\r\n" +
                "    \"wordCategory\" : \"NOUN\"\r\n" +
                "  }\r\n" +
                "}, {\r\n" +
                "  \"word\" : {\r\n" +
                "    \"word\" : \"two\",\r\n" +
                "    \"wordCategory\" : \"VERB\"\r\n" +
                "  }\r\n" +
                "}, {\r\n" +
                "  \"word\" : {\r\n" +
                "    \"word\" : \"three\",\r\n" +
                "    \"wordCategory\" : \"ADJECTIVE\"\r\n" +
                "  }\r\n" +
                "} ]"))
    }

    @Test
    fun `getWord by word and category return Word`() {
        val entity = restTemplate.getForEntity<String>("/api/v1/words/one/NOUN")

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        assertThat(entity.body, `is`("{\r\n" +
                "  \"word\" : {\r\n" +
                "    \"word\" : \"one\",\r\n" +
                "    \"wordCategory\" : \"NOUN\"\r\n" +
                "  }\r\n" +
                "}"))
    }

    @Test
    fun `getWord by word return Word`() {
        val entity = restTemplate.getForEntity<String>("/api/v1/words/one")

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        assertThat(entity.body, `is`("{\r\n" +
                "  \"word\" : {\r\n" +
                "    \"word\" : \"one\",\r\n" +
                "    \"wordCategory\" : \"NOUN\"\r\n" +
                "  }\r\n" +
                "}"))
    }

    @Test
    fun `putWord return Word`() {
        val wordRestDto = WordRestDto(WordBodyRestDto(null, WordCategory.ADJECTIVE))
        val entity = restTemplate.exchange("/api/v1/words/new", HttpMethod.PUT, HttpEntity(wordRestDto), String::class.java)

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        assertThat(entity.body, `is`("{\r\n" +
                "  \"word\" : {\r\n" +
                "    \"word\" : \"new\",\r\n" +
                "    \"wordCategory\" : \"ADJECTIVE\"\r\n" +
                "  }\r\n" +
                "}"))
    }

    @Test
    fun `getSentences return list of Sentece`() {
        val entity = restTemplate.getForEntity<String>("/api/v1/sentences/")

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        // TODO continue test
    }
}