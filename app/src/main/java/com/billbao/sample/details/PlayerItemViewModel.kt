package com.billbao.sample.details

import android.databinding.ObservableField
import com.billbao.data.model.Player

/**
 * Exposes the data to be used for player item.
 */
class PlayerItemViewModel(player: Player) {
    val player: ObservableField<Player>  = ObservableField(player)
}
