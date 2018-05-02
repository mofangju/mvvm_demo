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

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import com.billbao.sample.R
import com.billbao.sample.details.TeamDetailsActivity

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.isInternal
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers.not

@RunWith(AndroidJUnit4::class)
@LargeTest
class TeamListActivityIntentTest {
    @get:Rule
    var intentsTestRule = IntentsTestRule(TeamListActivity::class.java)

    @Before
    fun stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not<Intent>(isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @Test
    fun checkClickFirstTeamSortedByName() {
        // Scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.teams_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        intended(hasComponent(TeamDetailsActivity::class.java.name))
    }
}
