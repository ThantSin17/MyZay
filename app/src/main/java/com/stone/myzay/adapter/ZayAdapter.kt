package com.stone.myzay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stone.myzay.R
import com.stone.myzay.databinding.ItemZayBinding
import com.stone.myzay.response.Zay

class ZayAdapter :ListAdapter<Zay,ZayAdapter.ZayViewHolder>(
   object : DiffUtil.ItemCallback<Zay>(){
       override fun areItemsTheSame(oldItem: Zay, newItem: Zay): Boolean {
           return oldItem==newItem
       }

       override fun areContentsTheSame(oldItem: Zay, newItem: Zay): Boolean {
           return oldItem==newItem
       }
   }
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZayViewHolder {
        return ZayViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_zay,parent,false))
    }

    override fun onBindViewHolder(holder: ZayViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ZayViewHolder(itemView: ItemZayBinding) :RecyclerView.ViewHolder(itemView.root){
        private val binding=itemView
        fun bind(zay:Zay){
            binding.zay=zay

        }
    }
}