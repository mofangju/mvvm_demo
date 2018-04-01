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

package com.billbao.data;

import com.billbao.core.logger.CoreLogger;
import com.billbao.data.local.LocalNBADataSource;
import com.billbao.data.model.Player;
import com.billbao.data.model.Team;
import com.billbao.data.remote.RemoteNBADataSource;
import java.lang.ref.WeakReference;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load NBA data from the data sources into a local db as a cache.
 * <p>
 * It implements a synchronisation between locally persisted data and data obtained from the server, by using the
 * remote data source only if the local database doesn't exist or is empty.
 *
 */
public class NBARepositoryImpl implements NBARepository {
    private final RemoteNBADataSource mRemoteNBADataSource;
    private final LocalNBADataSource mLocalNBADataSource;

    public NBARepositoryImpl(final LocalNBADataSource localTeamDataSource,
                             final RemoteNBADataSource remotePlayerDataSource) {
        mLocalNBADataSource = localTeamDataSource;
        mRemoteNBADataSource = remotePlayerDataSource;
    }

    @Override
    public void getTeams(final GetTeamsCallback callback) {
        checkNotNull(callback);

        final GetTeamsCallback remoteCallback = new GetTeamsCallback() {
            @Override
            public void onTeamsLoaded(List<Team> teams) {
                remoteGetTeamsLoaded(teams, callback);
            }

            @Override
            public void onDataNotAvailable() {
                CoreLogger.d("No data from remote data source!");
                callback.onDataNotAvailable();
            }
        };

        final GetTeamsCallback localCallback = new GetTeamsCallback() {
            @Override
            public void onTeamsLoaded(List<Team> teams) {
                localGetTeamsLoaded(teams, callback);
            }

            @Override
            public void onDataNotAvailable() {
                CoreLogger.d("Local players are not available, try remote data source...");
                mRemoteNBADataSource.getTeams(remoteCallback);
            }
        };

        mLocalNBADataSource.getTeams(localCallback);
    }

    private void localGetTeamsLoaded(final List<Team> teams, final GetTeamsCallback callback) {
        callback.onTeamsLoaded(teams);
    }

    private void remoteGetTeamsLoaded(final List<Team> teams, final GetTeamsCallback callback) {
        mLocalNBADataSource.insertTeams(teams);
        callback.onTeamsLoaded(teams);
    }

    @Override
    public void getPlayers(final Team team, final GetPlayersCallback callback) {
        checkNotNull(callback);

        final GetPlayersCallback remoteCallback = new GetPlayersCallback() {
            @Override
            public void onPlayersLoaded(List<Player> players) {
                CoreLogger.d("Remote players are loaded.");
                remotePlayersLoaded(team, players, callback);
            }

            @Override
            public void onDataNotAvailable() {
                CoreLogger.d("Remote players are not available.");
                callback.onDataNotAvailable();
            }
        };

        final GetPlayersCallback localCallback = new GetPlayersCallback() {
            @Override
            public void onPlayersLoaded(List<Player> players) {
                CoreLogger.d("Local players is loaded.");
                localPlayersLoaded(team, players, callback);
            }

            @Override
            public void onDataNotAvailable() {
                CoreLogger.d("Local player is not available, try remote data source...");
                mRemoteNBADataSource.getPlayers(team, remoteCallback);
            }
        };

        mLocalNBADataSource.getPlayers(team, localCallback);
    }

    private void localPlayersLoaded(final Team team,
                                    final List<Player> players,
                                    final GetPlayersCallback callback) {
        callback.onPlayersLoaded(players);
    }

    private void remotePlayersLoaded(final Team team,
                                     final List<Player> players,
                                     final GetPlayersCallback callback) {
        mLocalNBADataSource.insertPlayers(team, players);
        callback.onPlayersLoaded(players);
    }
}
