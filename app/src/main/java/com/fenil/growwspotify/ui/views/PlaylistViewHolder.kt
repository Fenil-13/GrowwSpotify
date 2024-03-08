package com.fenil.growwspotify.ui.views

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fenil.growwspotify.data.remote.model.PlaylistItem
import com.fenil.growwspotify.databinding.ItemPlaylistBinding

class PlaylistViewHolder(
    private val binding: ItemPlaylistBinding,
    val onItemClick: (position: Any) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(playlistItem: PlaylistItem) {
        binding.root.setOnClickListener{
            onItemClick(playlistItem)
        }
        binding.tvPlaylistName.text = playlistItem.name.orEmpty()
        binding.tvTotalTrack.text = "Total Track : ${playlistItem.tracks?.total ?: "0"}"
        binding.tvOwnerName.text = "Owner : ${playlistItem.owner?.displayName}"

        Glide.with(binding.root.context).load(playlistItem.images?.firstOrNull()?.url).into(binding.ivPlaylist)
    }
}