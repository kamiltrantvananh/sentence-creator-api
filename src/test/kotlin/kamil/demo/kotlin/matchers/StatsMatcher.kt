package kamil.demo.kotlin.matchers

import kamil.demo.kotlin.model.Stats
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class StatsMatcher(
        private var sentenceIds: Matcher<MutableIterable<Long>>,
        private var text: Matcher<String>
) : TypeSafeMatcher<Stats>() {

    override fun describeTo(description: Description?) {
        description?.appendText("StatsMatcher = [")
                ?.appendText("sentenceIds = ")?.appendDescriptionOf(sentenceIds)
                ?.appendText(". text = ")?.appendDescriptionOf(text)
                ?.appendText("]")
    }

    override fun matchesSafely(item: Stats?): Boolean {
        return sentenceIds.matches(item?.sentenceIds) && text.matches(item?.text)
    }
}