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

package com.billbao.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.billbao.data.local.entity.TeamEntity;
import java.util.List;

/**
 * Data Access Object for the Team table.
 */
@Dao
public interface TeamDao {
    /**
     * load all teams in the database.
     */
    @Query("SELECT * FROM teams")
    List<TeamEntity> loadAll();

    /**
     * load the team via id from the database.
     */
    @Query("SELECT * FROM teams WHERE mId = :id")
    TeamEntity loadTeam(Integer id);

    /**
     * Insert all the teams in the database. If the team already exists, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TeamEntity> teamEntities);

    /**
     * Insert a team in the database. If the team already exists, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeam(TeamEntity teamEntity);

    // TODO: May need delete team in future
}
