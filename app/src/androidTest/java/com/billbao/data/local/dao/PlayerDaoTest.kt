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

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import com.billbao.data.utils.DataUtils
import com.billbao.data.local.AppDatabase
import com.billbao.data.local.entity.PlayerEntity
import com.billbao.data.local.entity.TeamEntity
import com.billbao.data.remote.json.TeamJson
import com.google.gson.reflect.TypeToken

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.io.IOException
import java.util.ArrayList

import org.hamcrest.core.Is.`is`
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class PlayerDaoTest {
    private lateinit var mDatabase: AppDatabase
    private lateinit var mTeamDao: TeamDao
    private lateinit var mPlayerDao: PlayerDao

    private lateinit var mTeamEntities: MutableList<TeamEntity>

    private lateinit var mTeamEntity0: TeamEntity
    private lateinit var mPlayerEntity0: PlayerEntity

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
        mPlayerDao = mDatabase.playerDao()

        val inputStream = InstrumentationRegistry.getTargetContext().assets.open("input.json")
        val teams = DataUtils.fromJsonStream<List<TeamJson>>(inputStream, object : TypeToken<ArrayList<TeamJson>>() {

        }.type)
        mTeamEntities = ArrayList()
        for (team in teams) {
            mTeamEntities.add(TeamEntity(team))
        }
        inputStream.close()

        mTeamDao.insertAll(mTeamEntities)

        mTeamEntity0 = mTeamEntities[0]
        val team0 = teams[0]
        val player0 = team0.players[0]
        mPlayerEntity0 = PlayerEntity(team0, player0)
    }

    @After
    fun tearDown() {
        mDatabase.close()
    }

    @Test
    fun testLoadInitialEmptyDB() {
        val playerEntities = mPlayerDao.loadPlayers(mTeamEntity0.id)
        assertThat(playerEntities.size, `is`(0))
    }

    @Test
    fun testInsertAndLoad() {
        mPlayerDao.insertPlayer(mPlayerEntity0)
        val playerEntity = mPlayerDao.loadPlayers(mTeamEntity0.id)[0]

        assertThat(playerEntity, `is`<PlayerEntity>(mPlayerEntity0))
    }
}

