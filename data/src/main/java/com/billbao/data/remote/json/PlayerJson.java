package com.billbao.data.remote.json;

import com.billbao.data.model.Player;
import com.google.common.base.Objects;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Immutable Player POJO to from JSON response.
 *
 * This is different from the {@link com.billbao.data.local.entity.PlayerEntity} in the database because
 * the player is hold in its {@link TeamJson}.
 */
public final class PlayerJson implements Player {
    @SerializedName("id")
    @Expose
    private final Integer mId;
    @SerializedName("first_name")
    @Expose
    private final String mFirstName;
    @SerializedName("last_name")
    @Expose
    private final String mLastName;
    @SerializedName("position")
    @Expose
    private final String mPosition;
    @SerializedName("number")
    @Expose
    private final Integer mNumber;

    public PlayerJson(Integer id, String firstName, String lastName, String position, Integer number) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mPosition = position;
        mNumber = number;
    }

    @Override
    public Integer getId() {
        return mId;
    }

    @Override
    public String getFirstName() {
        return mFirstName;
    }

    @Override
    public String getLastName() {
        return mLastName;
    }

    @Override
    public String getPosition() {
        return mPosition;
    }

    @Override
    public Integer getNumber() {
        return mNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerJson player = (PlayerJson) o;
        return Objects.equal(mId, player.mId) &&
                Objects.equal(mFirstName, player.mFirstName) &&
                Objects.equal(mLastName, player.mLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mFirstName, mLastName);
    }
}