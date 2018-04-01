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

import com.billbao.data.model.Player;
import com.billbao.data.model.Team;

import java.util.List;

public interface NBARepository {
    interface GetTeamsCallback {
        void onTeamsLoaded(List<Team> teams);
        void onDataNotAvailable();
    }

    interface GetPlayersCallback {
        void onPlayersLoaded(List<Player> players);
        void onDataNotAvailable();
    }

    void getTeams(GetTeamsCallback callback);

    void getPlayers(Team team, GetPlayersCallback callback);

    // It may need the insert and delete operations in future, as follows.
    // void insertPlayers(Team team);
    // void deleteTeam(Team team);
}
