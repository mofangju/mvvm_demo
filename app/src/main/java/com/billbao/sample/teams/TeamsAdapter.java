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

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.billbao.data.model.Team;
import com.billbao.sample.R;
import com.billbao.sample.base.BaseViewHolder;
import com.billbao.sample.databinding.TeamItemViewBinding;
import com.billbao.sample.teams.sort.SortType;
import com.billbao.sample.teams.sort.TeamSortedList;

import java.lang.ref.WeakReference;
import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private TeamSortedList mTeamSortedList;
    private WeakReference<TeamDetailsNavigator> mTeamDetailsNavigatorRef;

    public TeamsAdapter() {
        super();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TeamItemViewBinding teamViewBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.team_item_view,
                        parent,
                        false);
        return new TeamViewHolder(teamViewBinding);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mTeamSortedList.size();
    }

    /**
     * Accept new teams to replace previous in the adaptor.
     * @param teams
     */
    public void replaceData(List<Team> teams) {
        mTeamSortedList.setTeams(teams);
        notifyDataSetChanged();
    }

    /**
     * Trigger the adaptor to sort the team list based on the sort type
     * @param sortType
     */
    public void sort(SortType sortType) {
        mTeamSortedList.changeSortType(sortType);
        notifyDataSetChanged();
    }

    /**
     * Set the new team sorted list
     * @param teamSortedList
     */
    public void setTeamSortedList(TeamSortedList teamSortedList) {
        this.mTeamSortedList = teamSortedList;
    }

    /**
     * Set the team details navigator
     * @param teamDetailsNavigator
     */
    public void setTeamDetailsNavigator(TeamDetailsNavigator teamDetailsNavigator) {
        mTeamDetailsNavigatorRef = new WeakReference<>(teamDetailsNavigator);
    }

    public class TeamViewHolder extends BaseViewHolder {

        private TeamItemViewBinding mBinding;

        private TeamItemViewModel mTeamItemViewModel;

        public TeamViewHolder(TeamItemViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Team team = mTeamSortedList.get(position);

            mTeamItemViewModel = new TeamItemViewModel(team, mTeamDetailsNavigatorRef);
            mBinding.setViewModel(mTeamItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }
    }
}
