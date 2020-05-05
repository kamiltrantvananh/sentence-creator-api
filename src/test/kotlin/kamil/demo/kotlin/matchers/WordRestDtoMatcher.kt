package kamil.demo.kotlin.matchers

import kamil.demo.kotlin.model.rest.WordRestDto
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class WordRestDtoMatcher(
        val word: WordBodyRestDtoMatcher
) : TypeSafeMatcher<WordRestDto>() {

    override fun describeTo(description: Description?) {
        description
                ?.appendText("WordRestDtoMatcher: [")
                ?.appendText("word = ")?.appendDescriptionOf(word)
                ?.appendText("]")
    }

    override fun matchesSafely(item: WordRestDto?): Boolean {
        return word.matches(item?.word)
    }
}