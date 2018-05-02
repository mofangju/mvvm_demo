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

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.billbao.data.model.Team
import com.billbao.sample.BR
import com.billbao.sample.R
import com.billbao.sample.about.AboutActivity
import com.billbao.sample.base.BaseActivity
import com.billbao.sample.databinding.ActivityTeamListBinding
import com.billbao.sample.details.TeamDetailsActivity
import com.billbao.sample.teams.sort.SortType
import com.billbao.sample.teams.sort.TeamSortedList

import javax.inject.Inject

/**
 * An activity representing the NBA team list. This activity has different presentations for handset and
 * tablet-size devices. On handsets, the activity presents a list of NBA teams, which when touched,
 * lead to a [TeamDetailsActivity] representing team details. On tablets, the activity presents
 * the list of teams and team details side-by-side using two vertical panes.
 */
open class TeamListActivity : BaseActivity<ActivityTeamListBinding, TeamListViewModel>(), TeamDetailsNavigator {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
     */
    private var mTwoPane: Boolean = false

    @Inject
    override lateinit var viewModel: TeamListViewModel
        internal set

    @Inject
    internal lateinit var mTeamsAdapter: TeamsAdapter

    private var mActivityBinding: ActivityTeamListBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_team_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = viewDataBinding

        if (findViewById<View>(R.id.team_details_container) != null) {
            // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
            // If this view is present, then the activity should be in two-pane mode.
            mTwoPane = true
        }

        viewModel.navigator = this
        setUp()
    }

    private fun setUp() {
        setSupportActionBar(mActivityBinding!!.toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowHomeEnabled(false)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mActivityBinding!!.teamsRecyclerView.layoutManager = linearLayoutManager
        mActivityBinding!!.teamsRecyclerView.itemAnimator = DefaultItemAnimator()

        mTeamsAdapter.setTeamDetailsNavigator(this)

        val teamSortedList = TeamSortedList(mTeamsAdapter)
        mTeamsAdapter.setTeamSortedList(teamSortedList)

        mActivityBinding!!.teamsRecyclerView.adapter = mTeamsAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> showAbout()
            R.id.menu_sort -> showFilteringPopUpMenu()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showAbout() {
        val intent = AboutActivity.newIntent(this)
        startActivity(intent)
    }

    private fun showFilteringPopUpMenu() {
        val popup = PopupMenu(this, findViewById<View>(R.id.menu_sort))
        popup.menuInflater.inflate(R.menu.sort_teams, popup.menu)

        val listener = { item: MenuItem ->
            when (item.itemId) {
                R.id.sort_by_wins -> mTeamsAdapter.sort(SortType.WINS)
                R.id.sort_by_losses -> mTeamsAdapter.sort(SortType.LOSSES)
                else -> mTeamsAdapter.sort(SortType.FULLNAME)
            }
            true

        }
        popup.setOnMenuItemClickListener(listener)

        popup.show()
    }

    override fun openTeamDetails(team: Team) {
        if (mTwoPane) {
            // TODO: I do not have time to implement the fragment for tablet.
            // Note: {@link TeamListActivity} need to implement HasSupportFragmentInjector.
        } else {
            // arg as teamId
            val intent = TeamDetailsActivity.newIntent(this, team)
            startActivity(intent)
        }
    }
}
