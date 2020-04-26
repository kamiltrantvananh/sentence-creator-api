package kamil.demo.kotlin.types

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppProperties() {

    @Value("\${file.forbidden.words}")
    lateinit var forbiddenWords: String
}