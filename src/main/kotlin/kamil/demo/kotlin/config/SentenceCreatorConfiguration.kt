package kamil.demo.kotlin.config

import kamil.demo.kotlin.repository.SentenceRepository
import kamil.demo.kotlin.repository.WordRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SentenceCreatorConfiguration {

    @Bean
    fun dbInit(wordRepository: WordRepository, sentenceRepository: SentenceRepository) = ApplicationRunner {}
}