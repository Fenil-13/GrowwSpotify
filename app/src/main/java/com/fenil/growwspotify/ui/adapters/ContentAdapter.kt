package com.fenil.growwspotify.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fenil.growwspotify.data.model.AlbumItem
import com.fenil.growwspotify.data.model.ArtistItem
import com.fenil.growwspotify.data.model.PlaylistItem
import com.fenil.growwspotify.data.model.TrackItem
import com.fenil.growwspotify.databinding.ItemAlbumBinding
import com.fenil.growwspotify.databinding.ItemArtistBinding
import com.fenil.growwspotify.databinding.ItemPlaylistBinding
import com.fenil.growwspotify.databinding.ItemTrackBinding

class ContentAdapter(
    private val items: MutableList<Any>,
    private val onItemClick: (data: Any) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TRACK_VIEW_TYPE = 1
        private const val ALBUM_VIEW_TYPE = 2
        private const val PLAYLIST_VIEW_TYPE = 3
        private const val ARTIST_VIEW_TYPE = 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TRACK_VIEW_TYPE -> TrackViewHolder(
                ItemTrackBinding.inflate(inflater, parent, false),
                onItemClick
            )

            ALBUM_VIEW_TYPE -> AlbumViewHolder(
                ItemAlbumBinding.inflate(inflater, parent, false),
                onItemClick
            )

            ARTIST_VIEW_TYPE -> ArtistViewHolder(
                ItemArtistBinding.inflate(inflater, parent, false),
                onItemClick
            )

            PLAYLIST_VIEW_TYPE -> PlaylistViewHolder(
                ItemPlaylistBinding.inflate(inflater, parent, false),
                onItemClick
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TrackViewHolder -> holder.bind(items[position] as TrackItem)
            is AlbumViewHolder -> holder.bind(items[position] as AlbumItem)
            is ArtistViewHolder -> holder.bind(items[position] as ArtistItem)
            is PlaylistViewHolder -> holder.bind(items[position] as PlaylistItem)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TrackItem -> TRACK_VIEW_TYPE
            is AlbumItem -> ALBUM_VIEW_TYPE
            is ArtistItem -> ARTIST_VIEW_TYPE
            is PlaylistItem -> PLAYLIST_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    fun updateData(updatedItems: List<Any>) {
        val startPosition = items.size
        items.clear()
        items.addAll(updatedItems)
        notifyItemRangeInserted(startPosition, updatedItems.size)

//        val diffResult = DiffUtil.calculateDiff(ContentDiffCallback(items, updatedItems))
//        items.clear()
//        items.addAll(updatedItems)
//        diffResult.dispatchUpdatesTo(this)
    }

    fun clearList(){
        items.clear()
        notifyDataSetChanged()
    }


    private class ContentDiffCallback(
        private val oldItems: List<Any>,
        private val newItems: List<Any>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }
}