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
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.billbao.data.model.Team;
import com.google.common.base.Objects;

/**
 * Immutable model class for a Team.
 */
@Entity(tableName = "teams")
public final class TeamEntity implements Team {
    @PrimaryKey
    @NonNull
    private final Integer mId;
    private final Integer mWins;
    private final Integer mLosses;
    private final String mFullName;

    public TeamEntity(Team team) {
        this(team.getId(), team.getWins(), team.getLosses(), team.getFullName());
    }

    public TeamEntity(Integer id, Integer wins, Integer losses, String fullName) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamEntity team = (TeamEntity) o;
        return Objects.equal(mId, team.mId) &&
                Objects.equal(mFullName, team.mFullName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mFullName);
    }
}
