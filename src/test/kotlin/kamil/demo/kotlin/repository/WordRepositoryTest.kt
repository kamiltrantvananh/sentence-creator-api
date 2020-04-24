package kamil.demo.kotlin.repository

import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.types.WordCategory
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class WordRepositoryTest @Autowired constructor(
        val entityManager: TestEntityManager,
        val wordRepository: WordRepository) {

    @Test
    fun `findAll return list of all Words`() {
        val wordOne = Word("one", WordCategory.NOUN)
        val wordTwo = Word("two", WordCategory.VERB)

        entityManager.persist(wordOne)
        entityManager.persist(wordTwo)
        entityManager.flush()

        val actual = wordRepository.findAll()
        assertThat(actual, containsInAnyOrder(wordOne, wordTwo))
    }

    @Test
    fun `findByIdOrNull return Word`() {
        val wordOne = Word("one", WordCategory.NOUN)
        val wordTwo = Word("two", WordCategory.VERB)

        entityManager.persist(wordOne)
        entityManager.persist(wordTwo)
        entityManager.flush()

        val actual = wordRepository.findByIdOrNull(wordOne.id!!)
        assertThat(actual, `is`(wordOne))
    }

    @Test
    fun `findByIdOrNull return null`() {
        val wordOne = Word("one", WordCategory.NOUN)
        val wordTwo = Word("two", WordCategory.VERB)

        entityManager.persist(wordOne)
        entityManager.persist(wordTwo)
        entityManager.flush()

        val actual = wordRepository.findByIdOrNull(666L)
        assertNull(actual)
    }

    @Test
    fun `findByWord return list of one Word`() {
        val wordOne = Word("one", WordCategory.NOUN)
        val wordTwo = Word("two", WordCategory.VERB)

        entityManager.persist(wordOne)
        entityManager.persist(wordTwo)
        entityManager.flush()

        val actual = wordRepository.findByWord("one")
        assertThat(actual, containsInAnyOrder(wordOne))
    }

    @Test
    fun `findByWordAndWordCategory return Word`() {
        val wordOne = Word("one", WordCategory.NOUN)
        val wordTwo = Word("two", WordCategory.VERB)

        entityManager.persist(wordOne)
        entityManager.persist(wordTwo)
        entityManager.flush()

        val actual = wordRepository.findByWordAndWordCategory("one", WordCategory.NOUN)
        assertThat(actual, `is`(wordOne))
    }
}