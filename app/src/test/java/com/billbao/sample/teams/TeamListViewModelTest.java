package com.billbao.sample.teams;

import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.data.NBARepository;
import com.billbao.data.model.Team;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.Executor;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeamListViewModelTest {
    @Mock
    private NBARepository mRepository;
    @Mock
    private AppExecutors mAppExecutors;
    @Mock
    private Executor mExecutor;
    @Mock
    private List<Team> mTeams;
    @Captor
    private ArgumentCaptor<NBARepository.GetTeamsCallback> mGetTeamsCallbackCaptor;

    private TeamListViewModel mTeamListViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mTeamListViewModel = new TeamListViewModel(mRepository, mAppExecutors);
        when(mAppExecutors.mainThread()).thenReturn(mExecutor);
    }

    @Test
    public void testInitState() {
        assertThat(mTeamListViewModel.isEmpty.get(), is(true));
    }

    @Test
    public void testStartWithLoadedData() {
        mTeamListViewModel.start();

        verify(mRepository).getTeams(mGetTeamsCallbackCaptor.capture());

        mGetTeamsCallbackCaptor.getValue().onTeamsLoaded(mTeams); // trigger callback

        assertThat(mTeamListViewModel.isEmpty.get(), is(false));
    }

    @Test
    public void testStartWithoutLoadedData() {
        mTeamListViewModel.start();

        verify(mRepository).getTeams(mGetTeamsCallbackCaptor.capture());

        mGetTeamsCallbackCaptor.getValue().onDataNotAvailable(); // trigger callback

        assertThat(mTeamListViewModel.isEmpty.get(), is(true));
    }
}
