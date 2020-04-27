package kamil.demo.kotlin.matchers

import kamil.demo.kotlin.model.rest.SentenceBodyRestDto
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.time.LocalDateTime

class SentenceBodyRestDtoMatcher(
        private val id: Matcher<Long>,
        private val text: Matcher<String>,
        private val showDisplayedCount: Matcher<Int>,
        private val created: Matcher<LocalDateTime>
) : TypeSafeMatcher<SentenceBodyRestDto>() {

    override fun describeTo(description: Description?) {
        description
                ?.appendText("SentenceBodyRestDtoMatcher: [")
                ?.appendText("id = ")?.appendDescriptionOf(id)
                ?.appendText(", text = ")?.appendDescriptionOf(text)
                ?.appendText(", showDisplayedCount = ")?.appendDescriptionOf(showDisplayedCount)
                ?.appendText(", created = ")?.appendDescriptionOf(created)
                ?.appendText("]")
    }

    override fun matchesSafely(item: SentenceBodyRestDto?): Boolean {
        return id.matches(item?.id)
                && text.matches(item?.text)
                && showDisplayedCount.matches(item?.showDisplayedCount)
                && created.matches(item?.created)
    }
}