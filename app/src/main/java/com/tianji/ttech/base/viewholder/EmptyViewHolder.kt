package com.tianji.ttech.base.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tianji.ttech.base.BaseViewHolder
import com.tianji.ttech.databinding.HomeEmptyViewBinding

class EmptyViewHolder(itemView: View) :
    BaseViewHolder(itemView) {


    companion object {
        fun create(
            parent: ViewGroup,
        ): EmptyViewHolder {
            val binding = HomeEmptyViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            val holder = EmptyViewHolder(binding.root)
            holder.binding = binding
            binding.root.setOnClickListener(holder)
            return holder
        }
    }

    private lateinit var binding: HomeEmptyViewBinding


}