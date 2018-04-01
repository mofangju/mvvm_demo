package com.billbao.sample.teams.sort;

import com.billbao.data.model.Team;
import java.util.Comparator;

/**
 * Use enum type to hold the different comparators.
 */
public enum SortType {
    FULLNAME(new TeamFullNameComparator()),
    WINS(new TeamWinsComparator()),
    LOSSES(new TeamLossesComparator());

    private Comparator<Team> mComparator;

    SortType(Comparator<Team> comparator) {
        mComparator = comparator;
    }

    public Comparator<Team> comparator() {
        return mComparator;
    }

    private static class TeamFullNameComparator implements Comparator<Team> {
        @Override public int compare(Team first, Team second) {
            return first.getFullName().compareToIgnoreCase(second.getFullName());
        }
    }

    private static class TeamWinsComparator implements Comparator<Team> {
        @Override public int compare(Team first, Team second) {
            return first.getWins().compareTo(second.getWins());
        }
    }

    private static class TeamLossesComparator implements Comparator<Team> {
        @Override public int compare(Team first, Team second) {
            return first.getLosses().compareTo(second.getLosses());
        }
    }
}
