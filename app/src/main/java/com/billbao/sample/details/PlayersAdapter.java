package com.billbao.sample.details;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.billbao.data.model.Player;
import com.billbao.sample.R;
import com.billbao.sample.base.BaseViewHolder;
import com.billbao.sample.databinding.PlayerItemViewBinding;
import java.util.Collections;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<Player> mPlayers;

    public PlayersAdapter(List<Player> players) {
        mPlayers = Collections.unmodifiableList(players);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PlayerItemViewBinding playerViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.player_item_view,
                parent,
                false);
        return new PlayerViewHolder(playerViewBinding);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    /**
     * Accept new players to replace previous in the adaptor.
     * @param players
     */
    public void replaceData(List<Player> players) {
        mPlayers = Collections.unmodifiableList(players);
        notifyDataSetChanged();
    }

    public class PlayerViewHolder extends BaseViewHolder {
        private PlayerItemViewBinding mBinding;
        private PlayerItemViewModel mPlayerItemViewModel;

        public PlayerViewHolder(PlayerItemViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Player player = mPlayers.get(position);
            mPlayerItemViewModel = new PlayerItemViewModel(player);
            mBinding.setViewModel(mPlayerItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }
    }
}
