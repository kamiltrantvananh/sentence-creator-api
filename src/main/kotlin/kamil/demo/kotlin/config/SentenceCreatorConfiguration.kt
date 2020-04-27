package kamil.demo.kotlin.config

import kamil.demo.kotlin.repository.SentenceRepository
import kamil.demo.kotlin.repository.WordRepository
import kamil.demo.kotlin.service.sentences.SentencesService
import kamil.demo.kotlin.service.words.WordsService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * Configuration for Spring application.
 * Also added Swagger.
 */
@Configuration
@EnableSwagger2
class SentenceCreatorConfiguration {

    @Bean
    fun init(
            wordRepository: WordRepository,
            sentenceRepository: SentenceRepository,
            wordsService: WordsService,
            sentencesService: SentencesService
    ) = ApplicationRunner {}

    @Bean
    fun api(): Docket? {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
    }
}