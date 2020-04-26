package kamil.demo.kotlin.matchers

import kamil.demo.kotlin.model.Word
import kamil.demo.kotlin.types.WordCategory
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class WordMatcher(
        private val word: Matcher<String>,
        private val wordCategory: Matcher<WordCategory>) : TypeSafeMatcher<Word>() {

    override fun describeTo(description: Description?) {
        description
                ?.appendText("WordMatcher: [")
                ?.appendText("word = ")?.appendDescriptionOf(word)
                ?.appendText(", wordCategory = ")?.appendDescriptionOf(wordCategory)
                ?.appendText("]")
    }

    override fun matchesSafely(item: Word?): Boolean {
        return word.matches(item?.word) && wordCategory.matches(item?.wordCategory)
    }
}