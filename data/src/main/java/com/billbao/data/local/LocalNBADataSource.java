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

package com.billbao.data.local;

import com.billbao.core.logger.CoreLogger;
import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.data.NBARepository;
import com.billbao.data.local.dao.PlayerDao;
import com.billbao.data.local.dao.TeamDao;
import com.billbao.data.local.entity.PlayerEntity;
import com.billbao.data.local.entity.TeamEntity;
import com.billbao.data.model.Player;
import com.billbao.data.model.Team;
import com.billbao.data.utils.DataUtils;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class LocalNBADataSource implements NBARepository {
    private final AppDatabase mAppDatabase;
    private final AppExecutors mAppExecutors;

    public LocalNBADataSource(AppDatabase appDatabase, AppExecutors appExecutors) {
        mAppDatabase = appDatabase;
        mAppExecutors = appExecutors;
    }

    @Override
    public void getTeams(final GetTeamsCallback callback) {
        checkNotNull(callback);
        Runnable runnable = () -> {
            CoreLogger.d("Start local load teams...");
            List<TeamEntity> teamEntities = mAppDatabase.teamDao().loadAll();
            if (teamEntities != null && !teamEntities.isEmpty()) {
                List<Team> teams = DataUtils.fromTeamEntities(teamEntities);
                CoreLogger.d("Finished local load teams: size=" + teams.size());

                callback.onTeamsLoaded(teams);
            } else {
                CoreLogger.d("Finished local load teams as empty.");
                callback.onDataNotAvailable();
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getPlayers(Team team, GetPlayersCallback callback) {
        checkNotNull(callback);
        Runnable runnable = () -> {
            List<PlayerEntity> playerEntities =  mAppDatabase.playerDao().loadPlayers(team.getId());
            if (playerEntities != null && !playerEntities.isEmpty()) {
                List<Player> players = DataUtils.fromPlayerEntities(playerEntities);
                callback.onPlayersLoaded(players);
            } else {
                callback.onDataNotAvailable();
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    public void insertTeams(List<Team> teams) {
        TeamDao teamDao = mAppDatabase.teamDao();
        for (Team team : teams) {
            teamDao.insertTeam(new TeamEntity(team));
        }
    }

    public void insertPlayers(Team team, List<Player> players) {
        PlayerDao playerDao = mAppDatabase.playerDao();
        for (Player player : players) {
            playerDao.insertPlayer(new PlayerEntity(team, player));
        }
    }
}
