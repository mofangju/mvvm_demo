package com.billbao.data.remote.json;

import com.billbao.data.model.Team;
import com.google.common.base.Objects;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Immutable Team POJO to from JSON response.
 *
 * This is different from the {@link com.billbao.data.local.entity.TeamEntity} in the database because
 * {@link #mPlayers} hold reference to its players.
 */
public final class TeamJson implements Team {
    @SerializedName("wins")
    @Expose
    private final Integer mWins;
    @SerializedName("losses")
    @Expose
    private final Integer mLosses;
    @SerializedName("full_name")
    @Expose
    private final String mFullName;
    @SerializedName("id")
    @Expose
    private final Integer mId;
    @SerializedName("players")
    @Expose
    private final List<PlayerJson> mPlayers = null;

    public TeamJson(Integer wins, Integer losses, String fullName, Integer id) {
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

    /**
     * Note: This method is specific to {@link TeamJson}, which contains the mapping between a team and its players.
     */
    public List<PlayerJson> getPlayers() {
        return mPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamJson team = (TeamJson) o;
        return Objects.equal(mId, team.mId) &&
                Objects.equal(mFullName, team.mFullName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mFullName);
    }
}
