package com.example.glpokemon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.glpokemon.viewholder.BaseViewHolder

class SingleViewHolderAdapter<T, VH : BaseViewHolder<T>>(
    @LayoutRes private val layoutId: Int,
    private val getViewHolder: (itemView: View) -> VH
) : RecyclerView.Adapter<VH>() {

    private val mDataSet: MutableList<T> = mutableListOf()

    fun setData(dataSet: List<T>?) {
        mDataSet.clear()
        dataSet?.let { mDataSet.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = mDataSet.size
    fun getItem(position: Int) = mDataSet[position]
}