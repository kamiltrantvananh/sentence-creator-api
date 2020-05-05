package kamil.demo.kotlin.matchers

import kamil.demo.kotlin.model.rest.WordBodyRestDto
import kamil.demo.kotlin.types.WordCategory
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class WordBodyRestDtoMatcher(
        val word: Matcher<String>,
        val wordCategory: Matcher<WordCategory>
) : TypeSafeMatcher<WordBodyRestDto>() {

    override fun describeTo(description: Description?) {
        description
                ?.appendText("WordBodyRestDtoMatcher: [")
                ?.appendText("word = ")?.appendDescriptionOf(word)
                ?.appendText(", wordCategory = ")?.appendDescriptionOf(wordCategory)
                ?.appendText("]")
    }

    override fun matchesSafely(item: WordBodyRestDto?): Boolean {
        return word.matches(item?.word)
                && wordCategory.matches(item?.wordCategory)
    }
}