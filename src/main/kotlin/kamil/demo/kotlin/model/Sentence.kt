package kamil.demo.kotlin.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Sentence (
        var text: String,
        var showDisplayCount: Int? = 0,
        @Id @GeneratedValue var id: Long? = null)