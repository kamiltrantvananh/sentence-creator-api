package kamil.demo.kotlin.matchers

import kamil.demo.kotlin.model.Sentence
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class SentenceMatcher (
        private val text: Matcher<String>,
        private val showDisplayCount: Matcher<Int>) : TypeSafeMatcher<Sentence>() {

    override fun describeTo(description: Description?) {
        description
                ?.appendText("SentenceMatcher: [")
                ?.appendText("text = ")?.appendDescriptionOf(text)
                ?.appendText(", showDisplayCount = ")?.appendDescriptionOf(showDisplayCount)
                ?.appendText("]")
    }

    override fun matchesSafely(item: Sentence?): Boolean {
        return text.matches(item?.text) && showDisplayCount.matches(item?.showDisplayCount)
    }
}