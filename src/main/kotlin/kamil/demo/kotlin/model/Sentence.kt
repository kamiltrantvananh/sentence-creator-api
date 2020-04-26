package kamil.demo.kotlin.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Sentence (
        var noun: String,
        var verb: String,
        var adjective: String,
        var showDisplayCount: Int? = 0,
        var timestamp: LocalDateTime? = LocalDateTime.now(),
        @Id @GeneratedValue var id: Long? = null) {


    override fun toString(): String {
        return "Sentence(noun='$noun', verb='$verb', adjective='$adjective', showDisplayCount=$showDisplayCount, timestamp=$timestamp, id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sentence

        if (noun != other.noun) return false
        if (verb != other.verb) return false
        if (adjective != other.adjective) return false
        if (showDisplayCount != other.showDisplayCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = noun.hashCode()
        result = 31 * result + verb.hashCode()
        result = 31 * result + adjective.hashCode()
        result = 31 * result + (showDisplayCount ?: 0)
        result = 31 * result + (timestamp?.hashCode() ?: 0)
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }
}