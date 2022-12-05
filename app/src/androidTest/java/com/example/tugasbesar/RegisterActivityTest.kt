package com.example.tugasbesar


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun registerActivityTest() {
        //tes inputan kosong
        val appCompatButton = onView(
            allOf(
                withId(R.id.btnRegister), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll_view),
                        0
                    ),
                    1
                )
            )
        )
        appCompatButton.perform(scrollTo(), click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText = onView(
            allOf(
                withId(R.id.etRegisUsername),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutRegisUsername),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("q"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.etRegisPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutRegisPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("q"), closeSoftKeyboard())

        // tes repeat password tidak sama dengan password
        val textInputEditText3 = onView(
            allOf(
                withId(R.id.etRegisRepeatPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutRegisRepeatPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("w"), closeSoftKeyboard())

        // tes format email
        val textInputEditText4 = onView(
            allOf(
                withId(R.id.etEmail),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("q"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.etTanggalLahir),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutTanggalLahir),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(click())

        val appCompatButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        appCompatButton2.perform(scrollTo(), click())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.etTelepon),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutTelepon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("2"), closeSoftKeyboard())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.btnRegister), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll_view),
                        0
                    ),
                    1
                )
            )
        )
        appCompatButton3.perform(scrollTo(), click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.etRegisRepeatPassword), withText("w"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutRegisRepeatPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(replaceText("q"))

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.etRegisRepeatPassword), withText("q"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutRegisRepeatPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText8.perform(closeSoftKeyboard())

        //tes email unique
        val textInputEditText9 = onView(
            allOf(
                withId(R.id.etEmail), withText("q"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText9.perform(replaceText("q@q.com"))

        val textInputEditText10 = onView(
            allOf(
                withId(R.id.etEmail), withText("q@q.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText10.perform(closeSoftKeyboard())

        val appCompatButton4 = onView(
            allOf(
                withId(R.id.btnRegister), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll_view),
                        0
                    ),
                    1
                )
            )
        )
        appCompatButton4.perform(scrollTo(), click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText11 = onView(
            allOf(
                withId(R.id.etEmail), withText("q@q.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText11.perform(click())

        val textInputEditText12 = onView(
            allOf(
                withId(R.id.etEmail), withText("q@q.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText12.perform(click())

        val textInputEditText13 = onView(
            allOf(
                withId(R.id.etEmail), withText("q@q.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText13.perform(replaceText("q@w.com"))

        val textInputEditText14 = onView(
            allOf(
                withId(R.id.etEmail), withText("q@w.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText14.perform(closeSoftKeyboard())

        val appCompatButton5 = onView(
            allOf(
                withId(R.id.btnRegister), withText("Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scroll_view),
                        0
                    ),
                    1
                )
            )
        )
        appCompatButton5.perform(scrollTo(), click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
