package com.billbao.sample.details

import com.billbao.core.scheduler.executor.AppExecutors
import com.billbao.data.NBARepository
import com.billbao.data.model.Player
import com.billbao.data.model.Team

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class DetailsViewModelTest {
    @Mock
    private val mRepository: NBARepository? = null
    @Mock
    private val mAppExecutors: AppExecutors? = null
    @Mock
    private val mExecutor: Executor? = null
    @Mock
    private val mPlayers: List<Player>? = null
    @Captor
    private val mGetPlayersCallbackCaptor: ArgumentCaptor<NBARepository.GetPlayersCallback>? = null

    private lateinit var mDetailsViewModel: DetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mDetailsViewModel = DetailsViewModel(mRepository!!, mAppExecutors!!)
        `when`(mAppExecutors.mainThread()).thenReturn(mExecutor)
    }

    @Test
    fun testInitState() {
        assertThat(mDetailsViewModel.isEmpty.get(), `is`(true))
    }

    @Test
    fun testStartWithLoadedData() {
        mDetailsViewModel!!.start()
        verify<NBARepository>(mRepository).getPlayers(any<Team>(), mGetPlayersCallbackCaptor!!.capture())
        mGetPlayersCallbackCaptor.value.onPlayersLoaded(mPlayers) // trigger callback

        assertThat(mDetailsViewModel!!.isEmpty.get(), `is`(false))
    }

    @Test
    fun testStartWithoutLoadedData() {
        mDetailsViewModel!!.start()
        verify<NBARepository>(mRepository).getPlayers(any<Team>(), mGetPlayersCallbackCaptor!!.capture())
        mGetPlayersCallbackCaptor.value.onDataNotAvailable() // trigger callback

        assertThat(mDetailsViewModel!!.isEmpty.get(), `is`(true))
    }

}
