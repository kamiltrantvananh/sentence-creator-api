package kamil.demo.kotlin.repository

import kamil.demo.kotlin.model.Sentence
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class SentenceRepositoryTest  @Autowired constructor(
        val entityManager: TestEntityManager,
        val sentenceRepository: SentenceRepository) {

    @Test
    fun `findAll return list of all Sentences`() {
        val sentenceOne = Sentence("one")
        val sentenceTwo = Sentence("two")

        entityManager.persist(sentenceOne)
        entityManager.persist(sentenceTwo)
        entityManager.flush()

        val actual = sentenceRepository.findAll()
        MatcherAssert.assertThat(actual, Matchers.containsInAnyOrder(sentenceOne, sentenceTwo))
    }

    @Test
    fun `findByIdOrNull return Sentence`() {
        val sentenceOne = Sentence("one")
        val sentenceTwo = Sentence("two")

        entityManager.persist(sentenceOne)
        entityManager.persist(sentenceTwo)
        entityManager.flush()

        val actual = sentenceRepository.findByIdOrNull(sentenceOne.id!!)
        MatcherAssert.assertThat(actual, Matchers.`is`(sentenceOne))
    }

    @Test
    fun `findByIdOrNull return null`() {
        val sentenceOne = Sentence("one")
        val sentenceTwo = Sentence("two")

        entityManager.persist(sentenceOne)
        entityManager.persist(sentenceTwo)
        entityManager.flush()

        val actual = sentenceRepository.findByIdOrNull(666L)
        assertNull(actual)
    }

    @Test
    fun `findByText return list of one Sentence`() {
        val sentenceOne = Sentence("one")
        val sentenceTwo = Sentence("two")

        entityManager.persist(sentenceOne)
        entityManager.persist(sentenceTwo)
        entityManager.flush()

        val actual = sentenceRepository.findByText("one")
        MatcherAssert.assertThat(actual, Matchers.containsInAnyOrder(sentenceOne))
    }
}