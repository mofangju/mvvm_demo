package com.billbao.data.utils;

import com.billbao.data.local.entity.PlayerEntity;
import com.billbao.data.local.entity.TeamEntity;
import com.billbao.data.model.Player;
import com.billbao.data.model.PlayerImpl;
import com.billbao.data.model.Team;
import com.billbao.data.model.TeamImpl;
import com.billbao.data.remote.json.PlayerJson;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    public static <T> T fromJsonStream(InputStream inputStream, Type typeKey)
            throws JsonIOException, JsonSyntaxException {
        Reader reader = new InputStreamReader(inputStream);
        T ret = new Gson().fromJson(reader, typeKey);
        return ret;
    }

    public static List<Team> fromTeamEntities(List<TeamEntity> teamEntities) {
        List<Team> teams = new ArrayList<>();
        for (TeamEntity teamEntity : teamEntities) {
            teams.add(new TeamImpl(teamEntity));
        }
        return teams;
    }

    public static List<Player> fromPlayerJsons(List<PlayerJson> playerJsons) {
        List<Player> players = new ArrayList<>();
        for (PlayerJson playerJson : playerJsons) {
            players.add(new PlayerImpl(playerJson));
        }
        return players;
    }

    public static List<Player> fromPlayerEntities(List<PlayerEntity> playerEntities) {
        List<Player> players = new ArrayList<>();
        for (PlayerEntity playerEntity : playerEntities) {
            players.add(new PlayerImpl(playerEntity));
        }
        return players;
    }
}
