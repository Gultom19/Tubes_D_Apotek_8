package com.example.tugasbesar


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
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
                withId(R.id.etOTP),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutOTP),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("a"), closeSoftKeyboard())

        val textInputEditText6 = onView(
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
        textInputEditText6.perform(click())

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

        val textInputEditText7 = onView(
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
        textInputEditText7.perform(replaceText("1"), closeSoftKeyboard())

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

        val textInputEditText8 = onView(
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
        textInputEditText8.perform(replaceText("q"))

        val textInputEditText9 = onView(
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
        textInputEditText9.perform(closeSoftKeyboard())

        val textInputEditText10 = onView(
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
        textInputEditText10.perform(replaceText("dragneelnatsu128@gmail.com"))

        val textInputEditText11 = onView(
            allOf(
                withId(R.id.etEmail), withText("dragneelnatsu128@gmail.com"),
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
        textInputEditText11.perform(closeSoftKeyboard())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.btn_sendEmail),
                childAtPosition(
                    allOf(
                        withId(R.id.ll_email),
                        childAtPosition(
                            withId(R.id.linearLayout),
                            3
                        )
                    ),
                    1
                )
            )
        )
        appCompatImageButton.perform(scrollTo(), click())

        val textInputEditText12 = onView(
            allOf(
                withId(R.id.etOTP), withText("a"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutOTP),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText12.perform(replaceText("3085"))

        val textInputEditText13 = onView(
            allOf(
                withId(R.id.etOTP), withText("3085"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutOTP),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText13.perform(closeSoftKeyboard())

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
}
