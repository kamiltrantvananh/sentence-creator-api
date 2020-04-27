package kamil.demo.kotlin.rest

import kamil.demo.kotlin.matchers.SentenceBodyRestDtoMatcher
import kamil.demo.kotlin.matchers.SentenceRestDtoMatcher
import kamil.demo.kotlin.model.Sentence
import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.model.rest.SentenceRestDto
import kamil.demo.kotlin.model.rest.WordBodyRestDto
import kamil.demo.kotlin.model.rest.WordRestDto
import kamil.demo.kotlin.repository.SentenceRepository
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.types.WordCategory
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.any
import org.hamcrest.Matchers.hasItems
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.core.ParameterizedTypeReference
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

    private final val defaultTimestamp: LocalDateTime = LocalDateTime.parse("2020-04-27T00:00:01")
    private final val typeReferenceList = object : ParameterizedTypeReference<List<SentenceRestDto>>(){}
    var sentenceOneCounter = 3
    var sentenceTwoCounter = 1
    private val sentenceOne = Sentence("one", "two", "three", sentenceOneCounter, defaultTimestamp)
    private val sentenceTwo = Sentence("Yoda", "talk", "normal", sentenceTwoCounter, defaultTimestamp.minusDays(2))

    @BeforeAll
    fun setup() {
        wordRepository.save(Word("one", WordCategory.NOUN))
        wordRepository.save(Word("two", WordCategory.VERB))
        wordRepository.save(Word("three", WordCategory.ADJECTIVE))

        sentenceRepository.save(sentenceOne)
        sentenceRepository.save(sentenceTwo)
    }

    @AfterAll
    fun cleanUpAfterMethod() {
        wordRepository.deleteAll()
        sentenceRepository.deleteAll()
    }

    @Test
    fun `getWords return list of Word`() {
        val entity = restTemplate.getForEntity<String>("/words/")

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
        val entity = restTemplate.getForEntity<String>("/words/one/NOUN")

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
        val entity = restTemplate.getForEntity<String>("/words/one")

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
        val entity = restTemplate.exchange("/words/new", HttpMethod.PUT, HttpEntity(wordRestDto), String::class.java)

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        assertThat(entity.body, `is`("{\r\n" +
                "  \"word\" : {\r\n" +
                "    \"word\" : \"new\",\r\n" +
                "    \"wordCategory\" : \"ADJECTIVE\"\r\n" +
                "  }\r\n" +
                "}"))
    }

    @Test
    fun `getSentences return list of Sentence`() {
        val entity = restTemplate.exchange("/sentences/", HttpMethod.GET, null, typeReferenceList)

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        sentenceOneCounter = sentenceOneCounter.inc()
        sentenceTwoCounter = sentenceTwoCounter.inc()
        assertThat(entity.body, hasItems(
                SentenceRestDtoMatcher(
                        SentenceBodyRestDtoMatcher(
                                `is`(any(Long::class.java)),
                                `is`("${sentenceOne.noun} ${sentenceOne.verb} ${sentenceOne.adjective}"),
                                `is`(sentenceOneCounter),
                                `is`(defaultTimestamp)
                        )),
                SentenceRestDtoMatcher(
                        SentenceBodyRestDtoMatcher(
                                `is`(any(Long::class.java)),
                                `is`("${sentenceTwo.noun} ${sentenceTwo.verb} ${sentenceTwo.adjective}"),
                                `is`(sentenceTwoCounter),
                                `is`(defaultTimestamp.minusDays(2))
                        )
                )
        ))
    }

    @Test
    fun `getSentence return Sentence`() {
        val entity = restTemplate.getForEntity<SentenceRestDto>("/sentences/${sentenceOne.id}")

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        sentenceOneCounter = sentenceOneCounter.inc()
        assertThat(entity.body, SentenceRestDtoMatcher(
                SentenceBodyRestDtoMatcher(
                        `is`(any(Long::class.java)),
                        `is`("${sentenceOne.noun} ${sentenceOne.verb} ${sentenceOne.adjective}"),
                        `is`(sentenceOneCounter),
                        `is`(defaultTimestamp)
                )))
    }

    @Test
    fun `getSentenceAsYodaTalk return Sentence`() {
        val entity = restTemplate.getForEntity<SentenceRestDto>("/sentences/${sentenceOne.id}/yodaTalk")

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        sentenceOneCounter = sentenceOneCounter.inc()
        assertThat(entity.body, SentenceRestDtoMatcher(
                SentenceBodyRestDtoMatcher(
                        `is`(any(Long::class.java)),
                        `is`("${sentenceOne.adjective} ${sentenceOne.noun} ${sentenceOne.verb}"),
                        `is`(sentenceOneCounter),
                        `is`(defaultTimestamp)
                )))
    }

    @Test
    fun `generateSentence return Sentence`() {
        val entity = restTemplate.postForEntity("/sentences/generate", null, SentenceRestDto::class.java)

        assertThat(entity.statusCode, `is`(HttpStatus.OK))
        assertThat(entity.body, SentenceRestDtoMatcher(
                SentenceBodyRestDtoMatcher(
                        `is`(any(Long::class.java)),
                        `is`("one two three"),
                        `is`(any(Int::class.java)),
                        `is`(any(LocalDateTime::class.java))
                )))
    }
}