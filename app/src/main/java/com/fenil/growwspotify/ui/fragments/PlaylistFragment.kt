package com.fenil.growwspotify.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fenil.growwspotify.R
import com.fenil.growwspotify.data.model.PlaylistItem
import com.fenil.growwspotify.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    val args: PlaylistFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playlistItem = args.playlistItem
        renderUi(playlistItem)
    }

    private fun renderUi(playlistItem: PlaylistItem) {
        binding.tvPlaylistName.text = playlistItem.name
        binding.tvPlaylistDescription.text = playlistItem.description ?: "No description available"
        binding.tvOwnerName.text = "Owner: ${playlistItem.owner?.displayName ?: "Unknown"}"
        binding.tvTotalTracks.text = "Total Tracks: ${playlistItem.tracks?.total ?: 0}"

        Glide.with(requireContext())
            .load(playlistItem.images?.firstOrNull()?.url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.ivPlaylistCover)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}