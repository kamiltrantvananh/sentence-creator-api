package kamil.demo.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SentenceCreator

    fun main(args: Array<String>) {
        runApplication<SentenceCreator>(*args)
    }
