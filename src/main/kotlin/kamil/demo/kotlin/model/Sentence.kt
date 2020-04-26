package kamil.demo.kotlin.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Sentence (
        var text: String,
        var showDisplayCount: Int? = 0,
        @Id @GeneratedValue var id: Long? = null) {

    override fun toString(): String {
        return "Sentence: [ text = \"$text\", showDisplayCount = \"$showDisplayCount\"]"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sentence

        if (text != other.text) return false
        if (showDisplayCount != other.showDisplayCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + (showDisplayCount ?: 0)
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }


}