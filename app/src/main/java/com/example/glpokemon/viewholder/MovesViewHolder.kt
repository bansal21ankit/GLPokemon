package com.example.glpokemon.viewholder

import android.view.View
import androidx.annotation.LayoutRes
import com.example.glpokemon.R
import com.example.glpokemon.databinding.ItemMovesBinding
import com.example.glpokemon.network.Moves

class MovesViewHolder(itemView: View) : BaseViewHolder<Moves>(itemView) {
    private val binding = ItemMovesBinding.bind(itemView)

    companion object {
        @LayoutRes val LAYOUT_ID = R.layout.item_moves
    }

    override fun bind(data: Moves) {
        val moveName = data.move?.name ?: ""
        binding.moveNameTxt.text = moveName
    }
}