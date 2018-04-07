/**
 * Copyright (C) 2018 Bill Bao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.billbao.sample.teams

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.billbao.data.model.Team
import com.billbao.sample.R
import com.billbao.sample.base.BaseViewHolder
import com.billbao.sample.databinding.TeamItemViewBinding
import com.billbao.sample.teams.sort.SortType
import com.billbao.sample.teams.sort.TeamSortedList

import java.lang.ref.WeakReference

class TeamsAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private var mTeamSortedList: TeamSortedList? = null
    private var mTeamDetailsNavigatorRef: WeakReference<TeamDetailsNavigator>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val teamViewBinding = DataBindingUtil.inflate<TeamItemViewBinding>(
                LayoutInflater.from(parent.context),
                R.layout.team_item_view,
                parent,
                false)
        return TeamViewHolder(teamViewBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return mTeamSortedList!!.size()
    }

    /**
     * Accept new teams to replace previous in the adaptor.
     * @param teams
     */
    fun replaceData(teams: List<Team>) {
        mTeamSortedList!!.setTeams(teams)
        notifyDataSetChanged()
    }

    /**
     * Trigger the adaptor to sort the team list based on the sort type
     * @param sortType
     */
    fun sort(sortType: SortType) {
        mTeamSortedList!!.changeSortType(sortType)
        notifyDataSetChanged()
    }

    /**
     * Set the new team sorted list
     * @param teamSortedList
     */
    fun setTeamSortedList(teamSortedList: TeamSortedList) {
        this.mTeamSortedList = teamSortedList
    }

    /**
     * Set the team details navigator
     * @param teamDetailsNavigator
     */
    fun setTeamDetailsNavigator(teamDetailsNavigator: TeamDetailsNavigator) {
        mTeamDetailsNavigatorRef = WeakReference(teamDetailsNavigator)
    }

    inner class TeamViewHolder(private val mBinding: TeamItemViewBinding) : BaseViewHolder(mBinding.root) {

        private var mTeamItemViewModel: TeamItemViewModel? = null

        override fun onBind(position: Int) {
            val team = mTeamSortedList!!.get(position)

            mTeamItemViewModel = TeamItemViewModel(team, mTeamDetailsNavigatorRef!!)
            mBinding.viewModel = mTeamItemViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()
        }
    }
}
