package com.billbao.sample.details;

import android.databinding.ObservableField;
import com.billbao.data.model.Player;

/**
 * Exposes the data to be used for player item.
 */
public class PlayerItemViewModel {
    public final ObservableField<Player> player;

    public PlayerItemViewModel(Player player) {
        this.player = new ObservableField<>(player);
    }
}
