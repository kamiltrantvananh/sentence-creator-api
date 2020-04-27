package kamil.demo.kotlin.matchers

import kamil.demo.kotlin.model.Sentence
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.time.LocalDateTime

class SentenceMatcher (
        private val noun: Matcher<String>,
        private val verb: Matcher<String>,
        private val adjective: Matcher<String>,
        private val showDisplayCount: Matcher<Int>,
        private val timestamp: Matcher<LocalDateTime>) : TypeSafeMatcher<Sentence>() {

    override fun describeTo(description: Description?) {
        description
                ?.appendText("SentenceMatcher: [")
                ?.appendText("noun = ")?.appendDescriptionOf(noun)
                ?.appendText(", verb = ")?.appendDescriptionOf(verb)
                ?.appendText(", adjective = ")?.appendDescriptionOf(adjective)
                ?.appendText(", showDisplayCount = ")?.appendDescriptionOf(showDisplayCount)
                ?.appendText("]")
    }

    override fun matchesSafely(item: Sentence?): Boolean {
        return noun.matches(item?.noun)
                && verb.matches(item?.verb)
                && adjective.matches(item?.adjective)
                && showDisplayCount.matches(item?.showDisplayCount)
    }
}