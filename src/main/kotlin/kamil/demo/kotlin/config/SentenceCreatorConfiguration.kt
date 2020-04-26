package kamil.demo.kotlin.config

import kamil.demo.kotlin.repository.SentenceRepository
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.service.sentences.SentencesService
import kamil.demo.kotlin.service.words.WordsService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SentenceCreatorConfiguration {

    @Bean
    fun init(
            wordRepository: WordRepository,
            sentenceRepository: SentenceRepository,
            wordsService: WordsService,
            sentencesService: SentencesService
    ) = ApplicationRunner {}
}