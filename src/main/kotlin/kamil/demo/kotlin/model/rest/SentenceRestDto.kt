package kamil.demo.kotlin.model.rest

class SentenceRestDto(
        val sentence: SentenceBodyRestDto
) {
    override fun toString(): String {
        return "SentenceRestDto(sentence=$sentence)"
    }
}