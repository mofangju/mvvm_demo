package com.billbao.sample.teams

import com.billbao.core.scheduler.executor.AppExecutors
import com.billbao.data.NBARepository
import com.billbao.data.model.Team

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

import org.hamcrest.core.Is.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class TeamListViewModelTest {
    @Mock
    private val mRepository: NBARepository? = null
    @Mock
    private val mAppExecutors: AppExecutors? = null
    @Mock
    private val mExecutor: Executor? = null
    @Mock
    private val mTeams: List<Team>? = null
    @Captor
    private val mGetTeamsCallbackCaptor: ArgumentCaptor<NBARepository.GetTeamsCallback>? = null

    private var mTeamListViewModel: TeamListViewModel? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mTeamListViewModel = TeamListViewModel(mRepository!!, mAppExecutors!!)
        `when`(mAppExecutors.mainThread()).thenReturn(mExecutor)
    }

    @Test
    fun testInitState() {
        assertThat(mTeamListViewModel!!.isEmpty.get(), `is`(true))
    }

    @Test
    fun testStartWithLoadedData() {
        mTeamListViewModel!!.start()

        verify<NBARepository>(mRepository).getTeams(mGetTeamsCallbackCaptor!!.capture())

        mGetTeamsCallbackCaptor.value.onTeamsLoaded(mTeams) // trigger callback

        assertThat(mTeamListViewModel!!.isEmpty.get(), `is`(false))
    }

    @Test
    fun testStartWithoutLoadedData() {
        mTeamListViewModel!!.start()

        verify<NBARepository>(mRepository).getTeams(mGetTeamsCallbackCaptor!!.capture())

        mGetTeamsCallbackCaptor.value.onDataNotAvailable() // trigger callback

        assertThat(mTeamListViewModel!!.isEmpty.get(), `is`(true))
    }
}
