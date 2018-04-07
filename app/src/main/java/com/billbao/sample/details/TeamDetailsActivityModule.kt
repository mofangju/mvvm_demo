package com.billbao.sample.details

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

import com.billbao.core.scheduler.executor.AppExecutors
import com.billbao.data.NBARepository
import com.billbao.data.model.Player
import com.billbao.sample.R

import java.util.ArrayList

import dagger.Module
import dagger.Provides

@Module
class TeamDetailsActivityModule {

    @Provides
    internal fun provideDetailsViewModel(repository: NBARepository, appExecutors: AppExecutors): DetailsViewModel {
        return DetailsViewModel(repository, appExecutors)
    }

    @Provides
    internal fun providesPlayersAdapter(): PlayersAdapter {
        return PlayersAdapter(ArrayList<Player>())
    }

    @Provides
    internal fun providesLayoutManager(context: Context): RecyclerView.LayoutManager {
        return GridLayoutManager(context, GRID_LAYOUT_SPAN_COUNT)
    }

    @Provides
    internal fun providesItemDecoration(context: Context): RecyclerView.ItemDecoration {
        val r = context.resources
        val size = r.getDimensionPixelSize(R.dimen.player_grid_spacing)
        return GridSpacingItemDecoration(GRID_LAYOUT_SPAN_COUNT, size, true)
    }

    @Provides
    internal fun providesItemAnimator(): RecyclerView.ItemAnimator {
        return DefaultItemAnimator()
    }

    companion object {
        private val GRID_LAYOUT_SPAN_COUNT = 2
    }
}
