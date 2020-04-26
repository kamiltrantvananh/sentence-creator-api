package kamil.demo.kotlin.service.sentences

import kamil.demo.kotlin.model.Sentence

interface SentencesService {

    fun getAllSentences(): List<Sentence>

    fun getSentence(id: Long): Sentence

    fun generateSentence(): Sentence

}