package com.billbao.sample.teams

import android.databinding.ObservableField

import com.billbao.data.model.Team

import java.lang.ref.WeakReference

/**
 * Exposes the data to be used for team item.
 */
class TeamItemViewModel(private val team: Team, private val mTeamDetailsNavigatorWeakRef: WeakReference<TeamDetailsNavigator>) {
    val fullName: ObservableField<String> = ObservableField(team.fullName)
    val wins: ObservableField<String> = ObservableField(team.wins.toString())
    val losses: ObservableField<String> = ObservableField(team.losses.toString())

    fun onItemClick() {
        val teamDetailsNavigator = mTeamDetailsNavigatorWeakRef.get()
        teamDetailsNavigator?.openTeamDetails(team)
    }
}
