package com.example.fastcampus35project_4_03

import androidx.recyclerview.widget.RecyclerView
import com.example.fastcampus35project_4_03.databinding.ItemDetailBinding
import com.example.fastcampus35project_4_03.model.DetailItem

class DetailViewHolder(private val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DetailItem) {
        binding.item = item
    }
}