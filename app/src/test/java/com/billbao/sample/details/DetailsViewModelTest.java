package com.billbao.sample.details;

import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.data.NBARepository;
import com.billbao.data.model.Player;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.Executor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailsViewModelTest {
    @Mock
    private NBARepository mRepository;
    @Mock
    private AppExecutors mAppExecutors;
    @Mock
    private Executor mExecutor;
    @Mock
    private List<Player> mPlayers;
    @Captor
    private ArgumentCaptor<NBARepository.GetPlayersCallback> mGetPlayersCallbackCaptor;

    private DetailsViewModel mDetailsViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mDetailsViewModel = new DetailsViewModel(mRepository, mAppExecutors);
        when(mAppExecutors.mainThread()).thenReturn(mExecutor);
    }

    @Test
    public void testInitState() {
        assertThat(mDetailsViewModel.isEmpty.get(), is(true));
    }

    @Test
    public void testStartWithLoadedData() {
        mDetailsViewModel.start();
        verify(mRepository).getPlayers(any(), mGetPlayersCallbackCaptor.capture());
        mGetPlayersCallbackCaptor.getValue().onPlayersLoaded(mPlayers); // trigger callback

        assertThat(mDetailsViewModel.isEmpty.get(), is(false));
    }

    @Test
    public void testStartWithoutLoadedData() {
        mDetailsViewModel.start();
        verify(mRepository).getPlayers(any(), mGetPlayersCallbackCaptor.capture());
        mGetPlayersCallbackCaptor.getValue().onDataNotAvailable(); // trigger callback

        assertThat(mDetailsViewModel.isEmpty.get(), is(true));
    }
}
