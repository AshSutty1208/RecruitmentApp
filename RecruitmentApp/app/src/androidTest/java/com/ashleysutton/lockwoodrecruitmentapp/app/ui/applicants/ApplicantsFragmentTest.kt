package com.ashleysutton.lockwoodrecruitmentapp.app.ui.applicants

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.CursorMatchers.withRowString
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.ashleysutton.lockwoodrecruitmentapp.R
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import com.ashleysutton.lockwoodrecruitmentapp.app.ui.MainActivity
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers.hasToString
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ApplicantsFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        val launchFragmentIntent = buildLaunchFragmentIntent(R.id.applicantsFragment, null)
        activityRule.scenario.onActivity {
            it.startActivity(launchFragmentIntent)
        }
    }

    private fun buildLaunchFragmentIntent(destinationId: Int, argBundle: Bundle?): Intent =
        NavDeepLinkBuilder(InstrumentationRegistry.getInstrumentation().targetContext)
            .setGraph(R.navigation.applicants_nav_graph)
            .setComponentName(MainActivity::class.java)
            .setDestination(destinationId)
            .setArguments(argBundle)
            .createTaskStackBuilder().intents[0]

    @Test
    fun testCorrectApplicantIsFountOnClickOfFirstItem() {
        //Wait for UI to load
        Thread.sleep(2000)

        onView(withId(R.id.applicants_rv))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)

        onView(withId(R.id.company)).check(matches(withText("Harmoney")))
        onView(withId(R.id.age)).check(matches(withText("37")))
        onView(withId(R.id.email)).check(matches(withText("carvercooke@harmoney.com")))

        Thread.sleep(5000)
    }
}