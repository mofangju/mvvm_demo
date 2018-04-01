package com.billbao.data;

import com.billbao.data.local.LocalNBADataSource;
import com.billbao.data.model.Team;
import com.billbao.data.model.Player;
import com.billbao.data.remote.RemoteNBADataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NBARepositoryImplTest {
    @Mock
    private LocalNBADataSource mLocalNBADataSource;
    @Mock
    private RemoteNBADataSource mRemoteNBADataSource;
    @Mock
    private Team mTeam;
    @Mock
    private List<Team> mTeams;
    @Mock
    private List<Player> mPlayers;
    @Mock
    private NBARepository.GetTeamsCallback mGetTeamsCallback;
    @Mock
    private NBARepository.GetPlayersCallback mGetPlayersCallback;
    @Captor
    private ArgumentCaptor<NBARepository.GetTeamsCallback> mGetTeamsCallbackCaptor;
    @Captor
    private ArgumentCaptor<NBARepository.GetTeamsCallback> mGetTeamsCallbackCaptor2;
    @Captor
    private ArgumentCaptor<NBARepository.GetPlayersCallback> mGetPlayersCallbackCaptor;
    @Captor
    private ArgumentCaptor<NBARepository.GetPlayersCallback> mGetPlayersCallbackCaptor2;

    private NBARepositoryImpl mRepositoryImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mRepositoryImpl = new NBARepositoryImpl(mLocalNBADataSource, mRemoteNBADataSource);
    }

    @Test(expected = NullPointerException.class)
    public void testGetTeamsWithNullCallback() {
        mRepositoryImpl.getTeams(null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetPlayersWithNullCallback() {
        mRepositoryImpl.getPlayers(mTeam, null);
    }

    @Test
    public void testGetTeamsFromLocalDataSource() {
        mRepositoryImpl.getTeams(mGetTeamsCallback);

        verify(mLocalNBADataSource).getTeams(any());
    }

    @Test
    public void testGetTeamsWithLocalData() {
        mRepositoryImpl.getTeams(mGetTeamsCallback);
        verify(mLocalNBADataSource).getTeams(mGetTeamsCallbackCaptor.capture());

        // trigger callback from local
        mGetTeamsCallbackCaptor.getValue().onTeamsLoaded(mTeams);

        // using local db for callback
        verify(mLocalNBADataSource, times(1)).getTeams(any());
        // using remote data source is never called
        verify(mRemoteNBADataSource, times(0)).getTeams(any());

        verify(mGetTeamsCallback).onTeamsLoaded(mTeams);
    }

    @Test
    public void testGetTeamsNoLocalData() {
        mRepositoryImpl.getTeams(mGetTeamsCallback);
        verify(mLocalNBADataSource).getTeams(mGetTeamsCallbackCaptor.capture());

        // trigger no data from local
        mGetTeamsCallbackCaptor.getValue().onDataNotAvailable();

        verify(mRemoteNBADataSource).getTeams(mGetTeamsCallbackCaptor2.capture());

        // trigger no data from remote
        mGetTeamsCallbackCaptor2.getValue().onDataNotAvailable();

        verify(mGetTeamsCallback).onDataNotAvailable();
    }

    @Test
    public void testGetTeamsRemoteData() {
        mRepositoryImpl.getTeams(mGetTeamsCallback);
        verify(mLocalNBADataSource).getTeams(mGetTeamsCallbackCaptor.capture());

        // trigger no data from local
        mGetTeamsCallbackCaptor.getValue().onDataNotAvailable();

        verify(mRemoteNBADataSource).getTeams(mGetTeamsCallbackCaptor2.capture());

        // trigger remote data
        mGetTeamsCallbackCaptor2.getValue().onTeamsLoaded(mTeams);

        verify(mLocalNBADataSource).insertTeams(any());

        verify(mGetTeamsCallback).onTeamsLoaded(mTeams);
    }

    @Test
    public void testGetPlayersFromLocalDataSource() {
        mRepositoryImpl.getPlayers(mTeam, mGetPlayersCallback);

        verify(mLocalNBADataSource).getPlayers(any(), any());
    }

    @Test
    public void testGetPlayersWithLocalData() {
        mRepositoryImpl.getPlayers(mTeam, mGetPlayersCallback);
        verify(mLocalNBADataSource).getPlayers(any(), mGetPlayersCallbackCaptor.capture());

        // trigger callback from local
        mGetPlayersCallbackCaptor.getValue().onPlayersLoaded(mPlayers);

        // using local db for callback
        verify(mLocalNBADataSource, times(1)).getPlayers(any(), any());
        // using remote data source is never called
        verify(mRemoteNBADataSource, times(0)).getPlayers(any(), any());

        verify(mGetPlayersCallback).onPlayersLoaded(mPlayers);
    }

    @Test
    public void testGetPlayersNoLocalData() {
        mRepositoryImpl.getPlayers(mTeam, mGetPlayersCallback);
        verify(mLocalNBADataSource).getPlayers(any(), mGetPlayersCallbackCaptor.capture());

        // trigger no data from local
        mGetPlayersCallbackCaptor.getValue().onDataNotAvailable();

        verify(mRemoteNBADataSource).getPlayers(any(), mGetPlayersCallbackCaptor2.capture());

        // trigger no data from remote
        mGetPlayersCallbackCaptor2.getValue().onDataNotAvailable();

        verify(mGetPlayersCallback).onDataNotAvailable();
    }

    @Test
    public void testGetPlayerRemoteData() {
        mRepositoryImpl.getPlayers(mTeam, mGetPlayersCallback);
        verify(mLocalNBADataSource).getPlayers(any(), mGetPlayersCallbackCaptor.capture());

        // trigger no data from local
        mGetPlayersCallbackCaptor.getValue().onDataNotAvailable();

        verify(mRemoteNBADataSource).getPlayers(any(), mGetPlayersCallbackCaptor2.capture());

        // trigger remote data
        mGetPlayersCallbackCaptor2.getValue().onPlayersLoaded(mPlayers);

        verify(mLocalNBADataSource).insertPlayers(any(), any());

        verify(mGetPlayersCallback).onPlayersLoaded(mPlayers);
    }
}