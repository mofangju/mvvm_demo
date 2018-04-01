package com.billbao.data.model;

import com.google.common.base.Objects;

/**
 * Use Immutable object whenever possible.
 */
public final class TeamImpl implements Team {
    private final Integer mWins;
    private final Integer mLosses;
    private final String mFullName;
    private final Integer mId;

    public TeamImpl(Team team) {
        this(team.getId(), team.getWins(), team.getLosses(), team.getFullName());
    }

    public TeamImpl(Integer id, Integer wins, Integer losses, String fullName) {
        mWins = wins;
        mLosses = losses;
        mFullName = fullName;
        mId = id;
    }

    @Override
    public Integer getWins() {
        return mWins;
    }

    @Override
    public Integer getLosses() {
        return mLosses;
    }

    @Override
    public String getFullName() {
        return mFullName;
    }

    @Override
    public Integer getId() {
        return mId;
    }

    @Override
    public String toString() {
        return mFullName + " Wins:" + mWins + " Losses:" + mLosses;
    }

    // equals() and hashCode() are important for Hash related operations
    // We regard team id and full name can identify the team uniquely.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamImpl team = (TeamImpl) o;
        return Objects.equal(mId, team.mId) &&
                Objects.equal(mFullName, team.mFullName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mFullName);
    }
}
