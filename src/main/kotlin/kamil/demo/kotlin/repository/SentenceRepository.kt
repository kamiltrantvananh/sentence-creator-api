package kamil.demo.kotlin.repository

import kamil.demo.kotlin.model.Sentence
import org.springframework.data.repository.CrudRepository

/**
 * Crud repository for sentences.
 */
interface SentenceRepository : CrudRepository<Sentence, Long>