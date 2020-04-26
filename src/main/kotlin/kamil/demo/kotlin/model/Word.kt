package kamil.demo.kotlin.model

import kamil.demo.kotlin.types.WordCategory
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Word (
        var word: String?,
        var wordCategory: WordCategory,
        @Id @GeneratedValue var id: Long? = null) {

    override fun toString(): String {
        return "Word: [word = \"$word\", wordCategory = \"$wordCategory\", id = \"$id\"]"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Word

        if (word != other.word) return false
        if (wordCategory != other.wordCategory) return false

        return true
    }

    override fun hashCode(): Int {
        var result = word?.hashCode() ?: 0
        result = 31 * result + wordCategory.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }


}