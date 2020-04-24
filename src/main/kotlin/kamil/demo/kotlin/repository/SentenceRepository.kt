package kamil.demo.kotlin.repository

import kamil.demo.kotlin.model.Sentence
import org.springframework.data.repository.CrudRepository

interface SentenceRepository : CrudRepository<Sentence, Long> {

    fun findByText(text: String): Iterable<Sentence>
}