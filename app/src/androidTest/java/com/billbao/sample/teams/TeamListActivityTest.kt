/**
 * Copyright (C) 2018 Bill Bao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.billbao.sample.teams

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import com.billbao.core.util.EspressoIdlingResource
import com.billbao.sample.R
import com.billbao.sample.TestUtils

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.RecyclerView
import com.billbao.sample.TestUtils.currentActivity

/**
 * Tests for the tasks screen, the main screen which contains a list of all tasks.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class TeamListActivityTest {

    /**
     * [ActivityTestRule] is a JUnit [@Rule][Rule] to launch your activity under test.
     *
     *
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @get:Rule
    var mActivityRule = ActivityTestRule(TeamListActivity::class.java)

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests
     * significantly more reliable.
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun checkFirstTeamSortedByFullName() {
        sortByFullName()
        onView(withText(FIRST_TEAM_BY_NAME)).check(matches(isDisplayed()))
    }

    @Test
    fun checkFirstTeamSortedByWins() {
        sortByWins()
        onView(withText(FIRST_TEAM_BY_WINS)).check(matches(isDisplayed()))
    }

    @Test
    fun checkFirstTeamSortedByLosses() {
        sortByLosses()
        onView(withText(FIRST_TEAM_BY_LOSSES)).check(matches(isDisplayed()))
    }

    @Test
    fun checkScrollToLastTeamSortedByName() {
        sortByFullName()
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.teams_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(LAST_ITEM_NUM, scrollTo()))
        // Match the text in an item below the fold and check that it's displayed.
        onView(withText(LAST_TEAM_BY_NAME)).check(matches(isDisplayed()))
    }

    /**
     * The following tests are related to device rotation
     */
    @Test
    fun checkFirstTeamSortedByFullNameAfterRotation() {
        checkFirstTeamSortedByFullName()

        // Rotate the screen
        TestUtils.rotateOrientation(currentActivity)

        checkFirstTeamSortedByFullName()
    }

    @Test
    fun checkFirstTeamSortedByWinsAfterRotation() {
        checkFirstTeamSortedByWins()

        // Rotate the screen
        TestUtils.rotateOrientation(currentActivity)

        checkFirstTeamSortedByWins()
    }

    @Test
    fun checkFirstTeamSortedByLossesAfterRotation() {
        checkFirstTeamSortedByLosses()

        // Rotate the screen
        TestUtils.rotateOrientation(currentActivity)

        checkFirstTeamSortedByLosses()
    }

    @Test
    fun checkScrollToLastTeamSortedByNameAfterRotation() {
        checkScrollToLastTeamSortedByName()

        // Rotate the screen
        TestUtils.rotateOrientation(currentActivity)

        checkScrollToLastTeamSortedByName()
    }

    companion object {
        private const val LAST_ITEM_NUM = 29
        private const val FIRST_TEAM_BY_NAME = "Atlanta Hawks"
        private const val FIRST_TEAM_BY_WINS = "Memphis Grizzlies"
        private const val FIRST_TEAM_BY_LOSSES = "Houston Rockets"
        private const val LAST_TEAM_BY_NAME = "Washington Wizards"

        /**
         * The test utils are listed as follows.
         */
        private fun sortByFullName() {
            onView(withId(R.id.menu_sort)).perform(click())
            onView(withText(R.string.sort_by_name)).perform(click())
        }

        private fun sortByWins() {
            onView(withId(R.id.menu_sort)).perform(click())
            onView(withText(R.string.sort_by_wins)).perform(click())
        }

        private fun sortByLosses() {
            onView(withId(R.id.menu_sort)).perform(click())
            onView(withText(R.string.sort_by_losses)).perform(click())
        }
    }
}
