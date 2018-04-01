package com.billbao.sample.teams;

import com.billbao.data.model.Team;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.ref.WeakReference;
import static org.mockito.Mockito.verify;

public class TeamItemViewModelTest {
    @Mock
    private TeamDetailsNavigator mTeamDetailsNavigator;
    @Mock
    private Team mTeam;

    private TeamItemViewModel mTeamItemViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mTeamItemViewModel = new TeamItemViewModel(mTeam, new WeakReference<>(mTeamDetailsNavigator));
    }

    @Test
    public void testStartWithLoadedData() {
        mTeamItemViewModel.onItemClick();

        verify(mTeamDetailsNavigator).openTeamDetails(mTeam);
    }
}
