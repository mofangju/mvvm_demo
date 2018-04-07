package com.billbao.sample.teams.sort

import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback

import com.billbao.data.model.Team

import java.util.ArrayList

/**
 * TeamSortedList uses Adaptor Design Pattern to change the original SortedList with different type of [SortType].
 */
class TeamSortedList(adapter: RecyclerView.Adapter<*>) {

    private var mSortType = SortType.FULLNAME  // default value

    private val mSortedList: SortedList<Team>

    init {
        val sortedListBatchCallback = SortedList.BatchedCallback(object : SortedListAdapterCallback<Team>(adapter) {
            override fun compare(a1: Team, a2: Team): Int {
                return mSortType.comparator().compare(a1, a2)
            }

            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: Team, item2: Team): Boolean {
                return item1.id == item2.id
            }
        })

        mSortedList = SortedList(Team::class.java, sortedListBatchCallback)
    }

    fun size(): Int {
        return mSortedList.size()
    }

    fun setTeams(teams: List<Team>) {
        mSortedList.addAll(teams)
    }

    operator fun get(position: Int): Team {
        return mSortedList.get(position)
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
    fun changeSortType(sortType: SortType) {
        if (this.mSortType != sortType) {
            mSortedList.beginBatchedUpdates()
            this.mSortType = sortType
            val items = ArrayList<Team>()
            for (j in 0 until mSortedList.size()) {
                items.add(mSortedList.get(j))
            }
            mSortedList.clear()
            mSortedList.addAll(items)
            mSortedList.endBatchedUpdates()
        }
    }
}
