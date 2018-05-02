package com.billbao.sample.details

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList

import com.billbao.core.scheduler.executor.AppExecutors
import com.billbao.core.util.EspressoIdlingResource
import com.billbao.data.NBARepository
import com.billbao.data.model.Player
import com.billbao.data.model.Team
import com.billbao.sample.base.BaseViewModel

/**
 * Exposes the data to be used in the NBA team details screen.
 */

class DetailsViewModel(repository: NBARepository, appExecutors: AppExecutors) : BaseViewModel<Any>(repository, appExecutors) {
    /**
     * The player list item.
     */
    val items: ObservableList<Player> = ObservableArrayList()

    /**
     * [.isEmpty] means whether the player data is loaded as empty or not.
     * The value of [.items] can be empty in following two cases:
     * - the initial state is empty;
     * - the data is loaded as empty.
     * That is reason [.isEmpty] is introduced.
     */
    val isEmpty = ObservableBoolean(true)

    /**
     * The team info.
     */
    var team = ObservableField<Team>()

    private var mTeam: Team? = null

    fun setTeam(team: Team) {
        this.mTeam = team
        this.team.set(team)
    }

    fun start() {
        // You can change your data model in a background thread as long as it is not a collection.
        setIsLoading(true)

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment() // App is busy until further notice

        repository.getPlayers(mTeam, object : NBARepository.GetPlayersCallback {
            override fun onPlayersLoaded(players: List<Player>) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }
                setIsLoading(false)
                isEmpty.set(false)
                updatePlayers(players)
            }

            override fun onDataNotAvailable() {
                setIsLoading(false)
                isEmpty.set(true)
            }
        })
    }

    /**
     * Must in UI thread for collection update
     */
    private fun updatePlayers(players: List<Player>) {
        val runnable = {
            setIsLoading(false)
            if (!players.isEmpty()) {
                items.clear()
                items.addAll(players)
            }

        }
        appExecutors.mainThread().execute(runnable)
    }
}
