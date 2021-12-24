package com.example.glpokemon.viewholder

import android.view.View
import androidx.annotation.LayoutRes
import com.example.glpokemon.R
import com.example.glpokemon.databinding.ItemStatsBinding
import com.example.glpokemon.network.Stats

class StatsViewHolder(itemView: View) : BaseViewHolder<Stats>(itemView) {
    private val binding = ItemStatsBinding.bind(itemView)

    companion object {
        @LayoutRes val LAYOUT_ID = R.layout.item_stats
    }

    override fun bind(data: Stats) {
        val statName = data.stat?.name ?: ""
        val statBase = data.base_stat ?: 0
        binding.statNameTxt.text = itemView.context.getString(R.string.stat, statName, statBase)
    }
}