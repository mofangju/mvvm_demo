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

package com.billbao.sample.util

import android.app.ProgressDialog
import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.Observable
import android.databinding.ObservableList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import com.billbao.data.model.Player
import com.billbao.data.model.Team
import com.billbao.sample.R
import com.billbao.sample.details.PlayersAdapter
import com.billbao.sample.teams.TeamsAdapter

object CommonUtils {

    @JvmStatic
    @BindingAdapter("app:items")
    fun setItems(listView: RecyclerView, items: ObservableList<Team>) {
        val adapter = listView.adapter
        if (adapter != null) {
            if (adapter is TeamsAdapter) {
                adapter.replaceData(items)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:items")
    fun setPlayers(listView: RecyclerView, items: ObservableList<Player>) {
        val adapter = listView.adapter
        if (adapter != null) {
            if (adapter is PlayersAdapter) {
                adapter.replaceData(items)
            }
        }
    }

    fun showLoadingDialog(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }
}
