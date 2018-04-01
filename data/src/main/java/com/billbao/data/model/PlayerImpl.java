package com.billbao.data.model;

import com.google.common.base.Objects;

/**
 * Use Immutable object whenever possible.
 */
public final class PlayerImpl implements Player {
    private final Integer mId;
    private final String mFirstName;
    private final String mLastName;
    private final String mPosition;
    private final Integer mNumber;

    public PlayerImpl(Player player) {
        this(player.getId(), player.getFirstName(), player.getLastName(),
                player.getPosition(), player.getNumber());
    }

    public PlayerImpl(Integer id, String firstName, String lastName, String position, Integer number) {
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
        PlayerImpl player = (PlayerImpl) o;
        return Objects.equal(mId, player.mId) &&
                Objects.equal(mFirstName, player.mFirstName) &&
                Objects.equal(mLastName, player.mLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mFirstName, mLastName);
    }
}
