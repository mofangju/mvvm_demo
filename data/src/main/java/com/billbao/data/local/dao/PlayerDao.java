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
import com.billbao.data.local.entity.PlayerEntity;
import java.util.List;

/**
 * Data Access Object for the Players table.
 */
@Dao
public interface PlayerDao {
    /**
     * load all players of the team in the database.
     */
    @Query("SELECT * FROM players where mTeamId = :teamId")
    List<PlayerEntity> loadPlayers(Integer teamId);

    /**
     * Insert a player in the database. If the player already exists, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayer(PlayerEntity playerEntities);

    // TODO: May need delete player in future
}
