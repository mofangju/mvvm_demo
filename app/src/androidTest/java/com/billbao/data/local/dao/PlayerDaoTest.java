package com.billbao.data.local.dao;

import android.arch.persistence.room.Room;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.billbao.data.utils.DataUtils;
import com.billbao.data.local.AppDatabase;
import com.billbao.data.local.entity.PlayerEntity;
import com.billbao.data.local.entity.TeamEntity;
import com.billbao.data.remote.json.PlayerJson;
import com.billbao.data.remote.json.TeamJson;
import com.google.gson.reflect.TypeToken;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PlayerDaoTest {
    private AppDatabase mDatabase;
    private TeamDao mTeamDao;
    private PlayerDao mPlayerDao;

    private List<TeamEntity> mTeamEntities;

    private TeamEntity mTeamEntity0;
    private PlayerEntity mPlayerEntity0;

    @Before
    public void setUp() throws IOException {

        // using an in-memory database because the information stored here disappears when the process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
        mTeamDao = mDatabase.teamDao();
        mPlayerDao = mDatabase.playerDao();

        InputStream inputStream = InstrumentationRegistry.getTargetContext().getAssets().open("input.json");
        List<TeamJson> teams = DataUtils.fromJsonStream(inputStream, new TypeToken<ArrayList<TeamJson>>(){}.getType());
        mTeamEntities = new ArrayList<>();
        for(TeamJson team: teams) {
            mTeamEntities.add(new TeamEntity(team));
        }
        inputStream.close();

        mTeamDao.insertAll(mTeamEntities);

        mTeamEntity0 = mTeamEntities.get(0);
        TeamJson team0 = teams.get(0);
        PlayerJson player0 = team0.getPlayers().get(0);
        mPlayerEntity0 = new PlayerEntity(team0, player0);
    }

    @After
    public void tearDown() {
        mDatabase.close();
        mTeamDao = null;
        mPlayerDao = null;
    }

    @Test
    public void testLoadInitialEmptyDB() {
        List<PlayerEntity> playerEntities = mPlayerDao.loadPlayers(mTeamEntity0.getId());
        assertThat(playerEntities.size(), is(0));
    }

    @Test
    public void testInsertAndLoad() {
        mPlayerDao.insertPlayer(mPlayerEntity0);
        PlayerEntity playerEntity = mPlayerDao.loadPlayers(mTeamEntity0.getId()).get(0);

        assertThat(playerEntity, is(mPlayerEntity0));
    }
}

