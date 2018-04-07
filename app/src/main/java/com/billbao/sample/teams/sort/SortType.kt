package com.billbao.sample.teams.sort

import com.billbao.data.model.Team
import java.util.Comparator

/**
 * Use enum type to hold the different comparators.
 */
enum class SortType private constructor(private val mComparator: Comparator<Team>) {
    FULLNAME(TeamFullNameComparator()),
    WINS(TeamWinsComparator()),
    LOSSES(TeamLossesComparator());

    fun comparator(): Comparator<Team> {
        return mComparator
    }

    private class TeamFullNameComparator : Comparator<Team> {
        override fun compare(first: Team, second: Team): Int {
            return first.fullName.compareTo(second.fullName, ignoreCase = true)
        }
    }

    private class TeamWinsComparator : Comparator<Team> {
        override fun compare(first: Team, second: Team): Int {
            return first.wins!!.compareTo(second.wins)
        }
    }

    private class TeamLossesComparator : Comparator<Team> {
        override fun compare(first: Team, second: Team): Int {
            return first.losses!!.compareTo(second.losses)
        }
    }
}
