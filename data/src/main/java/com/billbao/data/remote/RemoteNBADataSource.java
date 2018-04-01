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

package com.billbao.data.remote;

import android.content.Context;

import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.data.NBARepository;
import com.billbao.data.model.TeamImpl;
import com.billbao.data.utils.DataUtils;
import com.billbao.data.model.Player;
import com.billbao.data.model.Team;
import com.billbao.data.remote.json.TeamJson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Get NBA data JSON input. The demo uses local JSON input file.
 * In real application, the JSON usually comes from REST API.
 */
public class RemoteNBADataSource implements NBARepository {
    private final Context mContext;
    private final AppExecutors mAppExecutors;

    interface NBARequestCallback {
        void onTeamPlayersMapLoaded(Map<Team, List<Player>> response);
        void onDataNotAvailable();
    }

    public RemoteNBADataSource(Context context, AppExecutors appExecutors) {
        mContext = context;
        mAppExecutors = appExecutors;
    }

    @Override
    public void getTeams(final GetTeamsCallback callback) {
        requestNBAData(new NBARequestCallback() {
            @Override
            public void onTeamPlayersMapLoaded(Map<Team, List<Player>> response) {
                List<Team> teams = new ArrayList<>(response.keySet());
                callback.onTeamsLoaded(teams);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPlayers(final Team team, final NBARepository.GetPlayersCallback callback) {
        requestNBAData(new NBARequestCallback() {
            @Override
            public void onTeamPlayersMapLoaded(Map<Team, List<Player>> response) {
                List<Player> players = new ArrayList<>(response.get(team));
                callback.onPlayersLoaded(players);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void requestNBAData(final NBARequestCallback callback) {
        Runnable runnable = () -> {
            try {
                Map<Team, List<Player>> teamPlayerMap = new ConcurrentHashMap<>();

                InputStream inputStream = mContext.getAssets().open("input.json");
                List<TeamJson> teams = DataUtils.fromJsonStream(inputStream,
                        new TypeToken<ArrayList<TeamJson>>(){}.getType());

                for(TeamJson team: teams) {
                    List<Player> players = DataUtils.fromPlayerJsons(team.getPlayers());
                    teamPlayerMap.put(new TeamImpl(team), players);
                }
                inputStream.close();
                callback.onTeamPlayersMapLoaded(Collections.unmodifiableMap(teamPlayerMap));
            } catch (IOException exception) {
                callback.onDataNotAvailable();
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}
