package com.example.practiceapp.features

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.practiceapp.R
import com.example.practiceapp.features.login.LoginActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private lateinit var stringToBeTyped: String

    @get:Rule
    var activityRule: ActivityScenarioRule<LoginActivity> =
        ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun initValidString() {
        stringToBeTyped = "chanbo@gmail.com"
    }

    @Test
    fun changeText_sameActivity() {
        onView(withId(R.id.etxEmail))
            .perform(typeText(stringToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(click())
    }
}
