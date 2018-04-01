package com.billbao.sample.teams;

import android.databinding.ObservableField;

import com.billbao.data.model.Team;

import java.lang.ref.WeakReference;

/**
 * Exposes the data to be used for team item.
 */
public class TeamItemViewModel {
    public final ObservableField<String> fullName;
    public final ObservableField<String> wins;
    public final ObservableField<String> losses;

    private final WeakReference<TeamDetailsNavigator> mTeamDetailsNavigatorWeakRef;
    private final Team mTeam;

    public TeamItemViewModel(final Team team, final WeakReference<TeamDetailsNavigator> navigatorRef) {
        mTeam = team;
        mTeamDetailsNavigatorWeakRef = navigatorRef;
        fullName = new ObservableField<>(mTeam.getFullName());
        wins = new ObservableField<>(mTeam.getWins().toString());
        losses = new ObservableField<>(mTeam.getLosses().toString());
    }

    public void onItemClick() {
        TeamDetailsNavigator teamDetailsNavigator = mTeamDetailsNavigatorWeakRef.get();
        if (teamDetailsNavigator != null) {
            teamDetailsNavigator.openTeamDetails(mTeam);
        }
    }
}
