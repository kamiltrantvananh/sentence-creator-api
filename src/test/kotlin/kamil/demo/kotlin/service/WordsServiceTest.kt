package kamil.demo.kotlin.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.service.words.WordService
import kamil.demo.kotlin.types.WordCategory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
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

    @Test
    fun `getWord return Optional Word`() {
        val wordOne = Word("one", WordCategory.NOUN)
        every { wordRepository.findByWordAndWordCategory("one", WordCategory.NOUN) } returns wordOne
        val result = wordService.getWord("one", WordCategory.NOUN)
        assertTrue(result.isPresent)
        assertEquals(result.get(), wordOne)
    }

    @Test
    fun `getWord return Optional empty`() {
        every { wordRepository.findByWordAndWordCategory("one", WordCategory.NOUN) } returns null
        val result = wordService.getWord("one", WordCategory.NOUN)
        assertFalse(result.isPresent)
    }
}