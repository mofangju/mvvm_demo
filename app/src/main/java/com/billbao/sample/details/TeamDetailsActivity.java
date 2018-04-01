package com.billbao.sample.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.billbao.data.model.Team;
import com.billbao.data.model.TeamImpl;
import com.billbao.sample.R;
import com.billbao.sample.BR;
import com.billbao.sample.base.BaseActivity;
import com.billbao.sample.databinding.ActivityTeamDetailsBinding;

import javax.inject.Inject;

/**
 * This activity represents a NBA team details.
 */
public class TeamDetailsActivity extends BaseActivity<ActivityTeamDetailsBinding, DetailsViewModel> {
    public static final String ARG_TEAM_ID = "com.billbao.sample.details.ARG_TEAM_ID";
    public static final String ARG_TEAM_WINS = "com.billbao.sample.details.ARG_TEAM_WINS";
    public static final String ARG_TEAM_LOSSES = "com.billbao.sample.details.ARG_TEAM_LOSSES";
    public static final String ARG_TEAM_FULLNAME = "com.billbao.sample.details.ARG_TEAM_FULLNAME";

    @Inject
    DetailsViewModel mViewModel;
    @Inject
    PlayersAdapter mPlayersAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.ItemDecoration mItemDecoration;
    @Inject
    RecyclerView.ItemAnimator mItemAnimator;

    private ActivityTeamDetailsBinding mActivityBinding;

    private Team mTeam;

    public static Intent newIntent(Context context, Team team) {
        Intent intent = new Intent(context, TeamDetailsActivity.class);
        intent.putExtra(TeamDetailsActivity.ARG_TEAM_ID, team.getId());
        intent.putExtra(TeamDetailsActivity.ARG_TEAM_WINS, team.getWins());
        intent.putExtra(TeamDetailsActivity.ARG_TEAM_LOSSES, team.getLosses());
        intent.putExtra(TeamDetailsActivity.ARG_TEAM_FULLNAME, team.getFullName());

        return intent;
    }

    public static Team fromIntent(Intent intent) {
        Integer id = intent.getIntExtra(ARG_TEAM_ID, 0);
        Integer wins = intent.getIntExtra(ARG_TEAM_WINS, 0);
        Integer losses = intent.getIntExtra(ARG_TEAM_LOSSES, 0);
        String name = intent.getStringExtra(ARG_TEAM_FULLNAME);
        Team team = new TeamImpl(id, wins, losses, name);
        return team;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_details;
    }

    @Override
    public DetailsViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        mTeam = fromIntent(intent);
        mViewModel.setTeam(mTeam);

        mActivityBinding = getViewDataBinding();

        setUp();
    }

    private void setUp() {
        setSupportActionBar(mActivityBinding.toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mActivityBinding.playersRecyclerView.setLayoutManager(mLayoutManager);
        mActivityBinding.playersRecyclerView.addItemDecoration(mItemDecoration);
        mActivityBinding.playersRecyclerView.setItemAnimator(mItemAnimator);
        mActivityBinding.playersRecyclerView.setAdapter(mPlayersAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
