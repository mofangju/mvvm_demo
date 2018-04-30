/**
 * Copyright (C) 2018 Bill Bao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.billbao.data.local.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import com.billbao.data.utils.DataUtils
import com.billbao.data.local.AppDatabase
import com.billbao.data.local.entity.TeamEntity
import com.billbao.data.remote.json.TeamJson
import com.google.gson.reflect.TypeToken

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

import org.hamcrest.collection.IsEmptyCollection.empty
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertThat

@RunWith(AndroidJUnit4::class)
class TeamDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mDatabase: AppDatabase
    private lateinit var mTeamDao: TeamDao

    private lateinit var mTeamEntities: MutableList<TeamEntity>

    @Before
    @Throws(IOException::class)
    fun setUp() {

        // using an in-memory database because the information stored here disappears when the process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase::class.java)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build()
        mTeamDao = mDatabase.teamDao()

        val inputStream = InstrumentationRegistry.getTargetContext().assets.open("input.json")
        val teams = DataUtils.fromJsonStream<List<TeamJson>>(inputStream, object : TypeToken<ArrayList<TeamJson>>() {

        }.type)
        mTeamEntities = ArrayList()
        for (team in teams) {
            mTeamEntities.add(TeamEntity(team))
        }
        inputStream.close()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mDatabase.close()
    }

    @Test
    fun loadInitialEmptyDB() {
        val teamEntities = mTeamDao.loadAll()
        assertThat(teamEntities, `is`(notNullValue()))
        assertThat(teamEntities, `is`(empty()))
    }

    @Test
    fun testInsertAllAndLoadAll() {
        mTeamDao.insertAll(mTeamEntities)
        val teamEntities = mTeamDao.loadAll()
        assertThat(teamEntities.size, `is`(30))
        assertThat(teamEntities[0], `is`(mTeamEntities[0]))
        assertThat(teamEntities[1], `is`(mTeamEntities[1]))
    }

    @Test
    fun testInsertAndLoad() {
        val mTeamEntity1 = mTeamEntities[0]
        mTeamDao.insertTeam(mTeamEntity1)
        val teamEntity = mTeamDao.loadTeam(mTeamEntity1.id)

        assertThat(teamEntity.id, `is`(mTeamEntity1.id))
    }
}


