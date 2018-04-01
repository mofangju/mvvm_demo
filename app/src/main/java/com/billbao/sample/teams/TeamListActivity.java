/**
 *  Copyright (C) 2018 Bill Bao
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.billbao.sample.teams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.billbao.data.model.Team;
import com.billbao.sample.BR;
import com.billbao.sample.R;
import com.billbao.sample.about.AboutActivity;
import com.billbao.sample.base.BaseActivity;
import com.billbao.sample.databinding.ActivityTeamListBinding;
import com.billbao.sample.details.TeamDetailsActivity;
import com.billbao.sample.teams.sort.SortType;
import com.billbao.sample.teams.sort.TeamSortedList;

import javax.inject.Inject;

/**
 * An activity representing the NBA team list. This activity has different presentations for handset and
 * tablet-size devices. On handsets, the activity presents a list of NBA teams, which when touched,
 * lead to a {@link TeamDetailsActivity} representing team details. On tablets, the activity presents
 * the list of teams and team details side-by-side using two vertical panes.
 */
public class TeamListActivity extends BaseActivity<ActivityTeamListBinding, TeamListViewModel>
        implements TeamDetailsNavigator {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
     */
    private boolean mTwoPane;

    @Inject
    TeamListViewModel mViewModel;

    @Inject
    TeamsAdapter mTeamsAdapter;

    private ActivityTeamListBinding mActivityBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_list;
    }

    @Override
    public TeamListViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = getViewDataBinding();

        if (findViewById(R.id.team_details_container) != null) {
            // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
            // If this view is present, then the activity should be in two-pane mode.
            mTwoPane = true;
        }

        mViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        setSupportActionBar(mActivityBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityBinding.included.teamsRecyclerView.setLayoutManager(linearLayoutManager);
        mActivityBinding.included.teamsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mTeamsAdapter.setTeamDetailsNavigator(this);

        TeamSortedList teamSortedList = new TeamSortedList(mTeamsAdapter);
        mTeamsAdapter.setTeamSortedList(teamSortedList);

        mActivityBinding.included.teamsRecyclerView.setAdapter(mTeamsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                showAbout();
                break;
            case R.id.menu_sort:
                showFilteringPopUpMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        Intent intent = AboutActivity.newIntent(this);
        startActivity(intent);
    }

    private void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.menu_sort));
        popup.getMenuInflater().inflate(R.menu.sort_teams, popup.getMenu());

        PopupMenu.OnMenuItemClickListener listener = (MenuItem item) ->  {
            switch (item.getItemId()) {
                case R.id.sort_by_wins:
                    mTeamsAdapter.sort(SortType.WINS);
                    break;
                case R.id.sort_by_losses:
                    mTeamsAdapter.sort(SortType.LOSSES);
                    break;
                default:
                    mTeamsAdapter.sort(SortType.FULLNAME);
                    break;
            }
            return true;

        };
        popup.setOnMenuItemClickListener(listener);

        popup.show();
    }

    @Override
    public void openTeamDetails(Team team) {
        if (mTwoPane) {
            // TODO: I do not have time to implement the fragment for tablet.
            // Note: {@link TeamListActivity} need to implement HasSupportFragmentInjector.
        } else {
            // arg as teamId
            Intent intent = TeamDetailsActivity.newIntent(this, team);
            startActivity(intent);
        }
    }
}
