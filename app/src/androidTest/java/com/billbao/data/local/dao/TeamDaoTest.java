package com.billbao.data.local.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.billbao.data.utils.DataUtils;
import com.billbao.data.local.AppDatabase;
import com.billbao.data.local.entity.TeamEntity;
import com.billbao.data.remote.json.TeamJson;
import com.google.gson.reflect.TypeToken;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TeamDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;
    private TeamDao mTeamDao;

    private List<TeamEntity> mTeamEntities;

    @Before
    public void setUp() throws IOException {

        // using an in-memory database because the information stored here disappears when the process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
        mTeamDao = mDatabase.teamDao();

        InputStream inputStream = InstrumentationRegistry.getTargetContext().getAssets().open("input.json");
        List<TeamJson> teams = DataUtils.fromJsonStream(inputStream, new TypeToken<ArrayList<TeamJson>>(){}.getType());
        mTeamEntities = new ArrayList<>();
        for(TeamJson team: teams) {
            mTeamEntities.add(new TeamEntity(team));
        }
        inputStream.close();
    }

    @After
    public void tearDown() throws IOException {
        mDatabase.close();
        mTeamDao = null;
        mTeamEntities = null;
    }

    @Test
    public void loadInitialEmptyDB() {
        List<TeamEntity> teamEntities = mTeamDao.loadAll();
        assertThat(teamEntities, is(notNullValue()));
        assertThat(teamEntities, is(empty()));
    }

    @Test
    public void testInsertAllAndLoadAll() {
        mTeamDao.insertAll(mTeamEntities);
        List<TeamEntity> teamEntities = mTeamDao.loadAll();
        assertThat(teamEntities.size(), is(30));
        assertThat(teamEntities.get(0), is(mTeamEntities.get(0)));
        assertThat(teamEntities.get(1), is(mTeamEntities.get(1)));
    }

    @Test
    public void testInsertAndLoad() {
        TeamEntity mTeamEntity1 = mTeamEntities.get(0);
        mTeamDao.insertTeam(mTeamEntity1);
        TeamEntity teamEntity = mTeamDao.loadTeam(mTeamEntity1.getId());

        assertThat(teamEntity.getId(), is(mTeamEntity1.getId()));
    }
}


