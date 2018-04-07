package com.billbao.sample.details

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.billbao.data.model.Player
import com.billbao.sample.R
import com.billbao.sample.base.BaseViewHolder
import com.billbao.sample.databinding.PlayerItemViewBinding
import java.util.Collections

class PlayersAdapter(players: List<Player>) : RecyclerView.Adapter<BaseViewHolder>() {
    private var mPlayers: List<Player>? = null

    init {
        mPlayers = Collections.unmodifiableList(players)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val playerViewBinding = DataBindingUtil.inflate<PlayerItemViewBinding>(
                LayoutInflater.from(parent.context),
                R.layout.player_item_view,
                parent,
                false)
        return PlayerViewHolder(playerViewBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return mPlayers!!.size
    }

    /**
     * Accept new players to replace previous in the adaptor.
     * @param players
     */
    fun replaceData(players: List<Player>) {
        mPlayers = Collections.unmodifiableList(players)
        notifyDataSetChanged()
    }

    inner class PlayerViewHolder(private val mBinding: PlayerItemViewBinding) : BaseViewHolder(mBinding.root) {
        private var mPlayerItemViewModel: PlayerItemViewModel? = null

        override fun onBind(position: Int) {
            val player = mPlayers!![position]
            mPlayerItemViewModel = PlayerItemViewModel(player)
            mBinding.viewModel = mPlayerItemViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()
        }
    }
}
