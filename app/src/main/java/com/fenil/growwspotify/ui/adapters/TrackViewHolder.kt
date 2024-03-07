package com.fenil.growwspotify.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fenil.growwspotify.data.model.TrackItem
import com.fenil.growwspotify.databinding.ItemTrackBinding

class TrackViewHolder(private val binding: ItemTrackBinding,val onItemClick: (position: Any) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(track: TrackItem) {
        binding.root.setOnClickListener{
            onItemClick(track)
        }
        binding.tvTrackName.text = track.name.orEmpty()
        binding.tvArtistName.text = track.artists?.map { it.name }?.joinToString(", ") ?: "Artists Unknown"
        binding.tvAlbumName.text = track.album?.name.orEmpty()
        binding.tvReleaseDate.text = track.album?.releaseDate.orEmpty()

        Glide.with(binding.root.context).load(track.album?.images?.firstOrNull()?.url).into(binding.imageViewAlbum)
    }
}