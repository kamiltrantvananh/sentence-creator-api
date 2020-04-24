package kamil.demo.kotlin.model

import kamil.demo.kotlin.types.WordCategory
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Word (
        var word: String?,
        var wordCategory: WordCategory,
        @Id @GeneratedValue var id: Long? = null
)