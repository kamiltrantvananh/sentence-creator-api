package kamil.demo.kotlin.service.sentences

import kamil.demo.kotlin.exceptions.SentenceNotExistException
import kamil.demo.kotlin.model.Sentence
import kamil.demo.kotlin.repository.SentenceRepository
import kamil.demo.kotlin.service.words.WordsService
import kamil.demo.kotlin.types.WordCategory
import org.springframework.stereotype.Service

@Service
class SentencesServiceImpl(
        private val sentenceRepository: SentenceRepository,
        private val wordsService: WordsService
): SentencesService {

    override fun getAllSentences(): List<Sentence> {
        return sentenceRepository.findAll()
                .map { s -> incDisplayCount(s) }
                .toList()
    }

    override fun getSentence(id: Long): Sentence {
        val sentence = sentenceRepository.findById(id);

        return if (sentence.isPresent) {
            incDisplayCount(sentence.get())
        } else {
            throw SentenceNotExistException("Sentence with id \"$id\" not exist.")
        }
    }

    override fun generateSentence(): Sentence {
        return sentenceRepository.save(
                Sentence(
                        wordsService.getRandomWordByCategory(WordCategory.NOUN).word,
                        wordsService.getRandomWordByCategory(WordCategory.VERB).word,
                        wordsService.getRandomWordByCategory(WordCategory.ADJECTIVE).word,
                        1
                )
        )
    }

    fun incDisplayCount(sentence: Sentence): Sentence {
        sentence.showDisplayCount = sentence.showDisplayCount?.inc()
        return sentenceRepository.save(sentence)
    }
}