package kamil.demo.kotlin.model.rest

import kamil.demo.kotlin.types.WordCategory

data class WordBodyRestDto(
        val word: String?,
        val wordCategory: WordCategory
)