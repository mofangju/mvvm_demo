package com.billbao.sample.details;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.core.util.EspressoIdlingResource;
import com.billbao.data.NBARepository;
import com.billbao.data.model.Player;
import com.billbao.data.model.Team;
import com.billbao.sample.base.BaseViewModel;

import java.util.List;

/**
 * Exposes the data to be used in the NBA team details screen.
 */
public class DetailsViewModel extends BaseViewModel {
    /**
     * The player list item.
     */
    public final ObservableList<Player> items = new ObservableArrayList<>();

    /**
     * {@link #isEmpty} means whether the player data is loaded as empty or not.
     * The value of {@link #items} can be empty in following two cases:
     *        - the initial state is empty;
     *        - the data is loaded as empty.
     * That is reason {@link #isEmpty} is introduced.
     */
    public final ObservableBoolean isEmpty = new ObservableBoolean(true);

    /**
     * The team info.
     */
    public ObservableField<Team> team = new ObservableField<>();

    private Team mTeam;

    public void setTeam(Team team) {
        this.mTeam = team;
        this.team.set(team);
    }

    public DetailsViewModel(NBARepository repository, AppExecutors appExecutors) {
        super(repository, appExecutors);
    }

    public void start() {
        // You can change your data model in a background thread as long as it is not a collection.
        setIsLoading(true);

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        getRepository().getPlayers(mTeam, new NBARepository.GetPlayersCallback() {
            @Override
            public void onPlayersLoaded(List<Player> players) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }
                setIsLoading(false);
                isEmpty.set(false);
                updatePlayers(players);
            }

            @Override
            public void onDataNotAvailable() {
                setIsLoading(false);
                isEmpty.set(true);
            }
        });
    }

    /**
     * Must in UI thread for collection update
     */
    private void updatePlayers(List<Player> players) {
        Runnable runnable = () -> {
            setIsLoading(false);
            if (players.size() == 0) {
                return;
            }
            items.clear();
            items.addAll(players);
        };
        getAppExecutors().mainThread().execute(runnable);
    }
}
