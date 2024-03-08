package com.fenil.growwspotify.ui.views

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fenil.growwspotify.data.remote.model.ArtistItem
import com.fenil.growwspotify.databinding.ItemArtistBinding

class ArtistViewHolder(private val binding: ItemArtistBinding, val onItemClick: (position: Any) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(artistItem: ArtistItem) {
        binding.root.setOnClickListener{
            onItemClick(artistItem)
        }
        binding.tvArtistName.text = artistItem.name.orEmpty()
        binding.tvFollowerCount.text = "Followers : ${artistItem.followers?.total ?: "0"}"
        binding.tvGenres.text = "Genres : ${artistItem.genres?.joinToString(", ") ?: "Genres Unknown"}"
        Glide.with(binding.root.context).load(artistItem.images?.firstOrNull()?.url).into(binding.ivArtist)
    }
}