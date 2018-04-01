/**
 *  Copyright (C) 2018 Bill Bao
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.billbao.sample.teams;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.billbao.core.logger.CoreLogger;
import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.core.util.EspressoIdlingResource;
import com.billbao.data.NBARepository;
import com.billbao.data.model.Team;
import com.billbao.sample.base.BaseViewModel;

import java.util.List;

/**
 * Exposes the data to be used in the NBA team list screen.
 */
public class TeamListViewModel extends BaseViewModel {

    /**
     * The team list item.
     */
    public final ObservableList<Team> items = new ObservableArrayList<>();

    /**
     * {@link #isEmpty} means whether the team data is loaded as empty or not.
     * The value of {@link #items} can be empty in following two cases:
     *        - the initial state is empty;
     *        - the data is loaded as empty.
     * That is reason {@link #isEmpty} is introduced.
     */
    public final ObservableBoolean isEmpty = new ObservableBoolean(true);

    public TeamListViewModel(NBARepository repository, AppExecutors appExecutors) {
        super(repository, appExecutors);
    }

    public void start() {
        CoreLogger.d("Start get teams...");

        // You can change your data model in a background thread as long as it is not a collection.
        setIsLoading(true);

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        getRepository().getTeams(new NBARepository.GetTeamsCallback() {
            @Override
            public void onTeamsLoaded(final List<Team> teams) {
                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                CoreLogger.d("Finished load teams: size=" + teams.size());
                setIsLoading(false);
                isEmpty.set(false);

                updateTeams(teams);
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
    private void updateTeams(final List<Team> teams) {
        Runnable runnable = () -> {
            if (teams.size() == 0) {
                return;
            }
            items.clear();
            items.addAll(teams);
        };
        getAppExecutors().mainThread().execute(runnable);
    }
}
