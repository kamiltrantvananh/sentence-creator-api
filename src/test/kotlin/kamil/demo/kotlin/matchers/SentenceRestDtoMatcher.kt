package kamil.demo.kotlin.matchers

import kamil.demo.kotlin.model.rest.SentenceBodyRestDto
import kamil.demo.kotlin.model.rest.SentenceRestDto
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class SentenceRestDtoMatcher(
        private val sentence: Matcher<SentenceBodyRestDto>
) : TypeSafeMatcher<SentenceRestDto>() {

    override fun describeTo(description: Description?) {
        description
                ?.appendText("SentenceBodyRestDto: [")
                ?.appendText("sentence = ")?.appendDescriptionOf(sentence)
                ?.appendText("]")
    }

    override fun matchesSafely(item: SentenceRestDto?): Boolean {
        return sentence.matches(item?.sentence)
    }
}