package com.billbao.sample.details;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.billbao.core.scheduler.executor.AppExecutors;
import com.billbao.data.NBARepository;
import com.billbao.sample.R;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class TeamDetailsActivityModule {
    private static final int GRID_LAYOUT_SPAN_COUNT = 2;

    @Provides
    DetailsViewModel provideDetailsViewModel(NBARepository repository, AppExecutors appExecutors) {
        return new DetailsViewModel(repository, appExecutors);
    }

    @Provides
    PlayersAdapter providesPlayersAdapter() {
        return new PlayersAdapter(new ArrayList<>());
    }

    @Provides
    RecyclerView.LayoutManager providesLayoutManager(Context context) {
        return new GridLayoutManager(context, GRID_LAYOUT_SPAN_COUNT);
    }

    @Provides
    RecyclerView.ItemDecoration providesItemDecoration(Context context) {
        Resources r = context.getResources();
        int size = r.getDimensionPixelSize(R.dimen.player_grid_spacing);
        return new GridSpacingItemDecoration(GRID_LAYOUT_SPAN_COUNT, size, true);
    }

    @Provides
    RecyclerView.ItemAnimator providesItemAnimator() {
        return new DefaultItemAnimator();
    }
}
