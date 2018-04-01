/**
 *  Copyright (C) 2018 Bill Bao
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.billbao.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.billbao.data.model.Team;
import com.billbao.data.model.Player;
import com.billbao.data.remote.json.TeamJson;
import com.google.common.base.Objects;

/**
 * Immutable model class for a Player. Use player id as primary key, and team id as foreign key.
 */
@Entity(
        tableName = "players",
        foreignKeys = {
                @ForeignKey(entity =  TeamEntity.class,
                        parentColumns =  "mId",
                        childColumns = "mTeamId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index(value = "mTeamId")}
)
public final class PlayerEntity implements Player {
    @PrimaryKey
    @NonNull
    private final Integer mId;
    private final Integer mTeamId;
    private final String mFirstName;
    private final String mLastName;
    private final String mPosition;
    private final Integer mNumber;

    public PlayerEntity(Team team, Player player) {
        this(player.getId(), team.getId(), player.getFirstName(), player.getLastName(),
                player.getPosition(), player.getNumber());
    }

    public PlayerEntity(Integer id, Integer teamId, String firstName,
                        String lastName, String position, Integer number) {
        mId = id;
        mTeamId = teamId;
        mFirstName = firstName;
        mLastName = lastName;
        mPosition = position;
        mNumber = number;
    }

    @Override
    public Integer getId() {
        return mId;
    }

    /**
     * Note: This method is specific to {@link PlayerEntity}.
     *       Hence the DB can implements the mapping between a team and its players.
     */
    public Integer getTeamId() {
        return mTeamId;
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
        PlayerEntity player = (PlayerEntity) o;
        return Objects.equal(mId, player.mId) &&
                Objects.equal(mTeamId, player.mTeamId) &&
                Objects.equal(mFirstName, player.mFirstName) &&
                Objects.equal(mLastName, player.mLastName) &&
                Objects.equal(mPosition, player.mPosition) &&
                Objects.equal(mNumber, player.mNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTeamId, mFirstName, mLastName, mPosition, mNumber);
    }
}
