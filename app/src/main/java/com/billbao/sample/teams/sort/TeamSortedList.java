package com.billbao.sample.teams.sort;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.billbao.data.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * TeamSortedList uses Adaptor Design Pattern to change the original SortedList with different type of {@link SortType}.
 */
public class TeamSortedList {

    private SortType mSortType = SortType.FULLNAME;  // default value

    private SortedList<Team> mSortedList;

    public TeamSortedList(final RecyclerView.Adapter adapter) {
        SortedList.BatchedCallback<Team> sortedListBatchCallback
                = new SortedList.BatchedCallback<>(new SortedListAdapterCallback<Team>(adapter) {
            @Override public int compare(Team a1, Team a2) {
                return mSortType.comparator().compare(a1, a2);
            }

            @Override public boolean areContentsTheSame(Team oldItem, Team newItem) {
                return oldItem.equals(newItem);
            }

            @Override public boolean areItemsTheSame(Team item1, Team item2) {
                return item1.getId().equals(item2.getId());
            }
        });

        mSortedList = new SortedList<>(Team.class, sortedListBatchCallback);
    }

    public int size() {
        return mSortedList.size();
    }

    public void setTeams(List<Team> teams) {
        mSortedList.addAll(teams);
    }

    public Team get(int position) {
        return mSortedList.get(position);
    }

    /**
     * Please refer to https://medium.com/@nullthemall/sort-sortedlist-by-different-criteria-812003ba6f1
     * The author failed to find proper way to notify SortedList that criteria has changed:
     * - Called recalculatePositionOfItemAt() for each item at index and failed.
     * - Called updateItem() for each item and failed.
     *
     * Then the author decided to go bruteforce on it, simply removing all items and re-adding them all over again.
     *
     * TODO: Need more research on this if the performance is really an important factor to be considered.
     *
     */
    public void changeSortType(SortType sortType) {
        if (!this.mSortType.equals(sortType)) {
            mSortedList.beginBatchedUpdates();
            this.mSortType = sortType;
            List<Team> items = new ArrayList<>();
            for (int j = 0; j < mSortedList.size(); j++) {
                items.add(mSortedList.get(j));
            }
            mSortedList.clear();
            mSortedList.addAll(items);
            mSortedList.endBatchedUpdates();
        }
    }
}
