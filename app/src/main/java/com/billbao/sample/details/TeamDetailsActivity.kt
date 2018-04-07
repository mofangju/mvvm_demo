package com.billbao.sample.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView

import com.billbao.data.model.Team
import com.billbao.data.model.TeamImpl
import com.billbao.sample.R
import com.billbao.sample.BR
import com.billbao.sample.base.BaseActivity
import com.billbao.sample.databinding.ActivityTeamDetailsBinding

import javax.inject.Inject

/**
 * This activity represents a NBA team details.
 */
class TeamDetailsActivity : BaseActivity<ActivityTeamDetailsBinding, DetailsViewModel>() {

    @Inject
    override lateinit var viewModel: DetailsViewModel
        internal set
    @Inject
    internal lateinit var mPlayersAdapter: PlayersAdapter
    @Inject
    internal lateinit var mLayoutManager: RecyclerView.LayoutManager
    @Inject
    internal lateinit var mItemDecoration: RecyclerView.ItemDecoration
    @Inject
    internal lateinit var mItemAnimator: RecyclerView.ItemAnimator

    private var mActivityBinding: ActivityTeamDetailsBinding? = null

    private var mTeam: Team? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_team_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the Intent that started this activity and extract the string
        val intent = intent
        mTeam = fromIntent(intent)
        viewModel!!.setTeam(mTeam!!)

        mActivityBinding = viewDataBinding

        setUp()
    }

    private fun setUp() {
        setSupportActionBar(mActivityBinding!!.toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        mActivityBinding!!.playersRecyclerView.layoutManager = mLayoutManager
        mActivityBinding!!.playersRecyclerView.addItemDecoration(mItemDecoration)
        mActivityBinding!!.playersRecyclerView.itemAnimator = mItemAnimator
        mActivityBinding!!.playersRecyclerView.adapter = mPlayersAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel!!.start()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        val ARG_TEAM_ID = "com.billbao.sample.details.ARG_TEAM_ID"
        val ARG_TEAM_WINS = "com.billbao.sample.details.ARG_TEAM_WINS"
        val ARG_TEAM_LOSSES = "com.billbao.sample.details.ARG_TEAM_LOSSES"
        val ARG_TEAM_FULLNAME = "com.billbao.sample.details.ARG_TEAM_FULLNAME"

        fun newIntent(context: Context, team: Team): Intent {
            val intent = Intent(context, TeamDetailsActivity::class.java)
            intent.putExtra(TeamDetailsActivity.ARG_TEAM_ID, team.id)
            intent.putExtra(TeamDetailsActivity.ARG_TEAM_WINS, team.wins)
            intent.putExtra(TeamDetailsActivity.ARG_TEAM_LOSSES, team.losses)
            intent.putExtra(TeamDetailsActivity.ARG_TEAM_FULLNAME, team.fullName)

            return intent
        }

        fun fromIntent(intent: Intent): Team {
            val id = intent.getIntExtra(ARG_TEAM_ID, 0)
            val wins = intent.getIntExtra(ARG_TEAM_WINS, 0)
            val losses = intent.getIntExtra(ARG_TEAM_LOSSES, 0)
            val name = intent.getStringExtra(ARG_TEAM_FULLNAME)
            return TeamImpl(id, wins, losses, name)
        }
    }
}
