package com.fenil.growwspotify.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fenil.growwspotify.data.model.AlbumItem
import com.fenil.growwspotify.databinding.ItemAlbumBinding

class AlbumViewHolder(private val binding: ItemAlbumBinding, val onItemClick: (position: Any) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(albumItem: AlbumItem) {
        binding.root.setOnClickListener{
            onItemClick(albumItem)
        }
        binding.tvAlbumName.text = albumItem.name.orEmpty()
        binding.tvArtistName.text = albumItem.artists?.map { it.name }?.joinToString(", ") ?: "Artists Unknown"
        binding.tvTotalTrack.text = "Total Track : ${albumItem.totalTracks ?: "0"}"
        binding.tvReleaseDate.text = albumItem.releaseDate.orEmpty()

        Glide.with(binding.root.context).load(albumItem.images?.firstOrNull()?.url).into(binding.ivAlbum)
    }
}